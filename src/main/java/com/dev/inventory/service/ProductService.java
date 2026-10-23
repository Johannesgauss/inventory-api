package com.dev.inventory.service;

import com.dev.inventory.dto.ProductRequestDTO;
import com.dev.inventory.dto.ProductResponseDTO;
import com.dev.inventory.exception.BusinessException;
import com.dev.inventory.exception.ResourceNotFoundException;
import com.dev.inventory.model.Product;
import com.dev.inventory.model.ProductCategory;
import com.dev.inventory.model.ProductStatus;
import com.dev.inventory.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository repository;

    @Transactional(readOnly = true)
    public List<ProductResponseDTO> findAll(String query, ProductCategory category) {
        List<Product> products;
        if (query != null && !query.trim().isEmpty()) {
            products = repository.search(query.trim());
        } else if (category != null) {
            products = repository.findByCategory(category);
        } else {
            products = repository.findAll();
        }
        return products.stream()
                .map(ProductResponseDTO::fromEntity)
                .toList();
    }

    @Transactional(readOnly = true)
    public ProductResponseDTO findById(Long id) {
        Product product = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product with ID " + id + " not found."));
        return ProductResponseDTO.fromEntity(product);
    }

    @Transactional
    public ProductResponseDTO create(ProductRequestDTO dto) {
        if (repository.existsBySku(dto.sku())) {
            throw new BusinessException("A product with SKU '" + dto.sku() + "' already exists.");
        }

        Product product = Product.builder()
                .sku(dto.sku().toUpperCase().trim())
                .name(dto.name().trim())
                .description(dto.description())
                .price(dto.price())
                .quantityInStock(dto.quantityInStock())
                .minStock(dto.minStock())
                .category(dto.category())
                .status(dto.status() != null ? dto.status() : ProductStatus.ACTIVE)
                .build();

        Product saved = repository.save(product);
        return ProductResponseDTO.fromEntity(saved);
    }

    @Transactional
    public ProductResponseDTO update(Long id, ProductRequestDTO dto) {
        Product product = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product with ID " + id + " not found."));

        if (repository.existsBySkuAndIdNot(dto.sku(), id)) {
            throw new BusinessException("Another product with SKU '" + dto.sku() + "' already exists.");
        }

        product.setSku(dto.sku().toUpperCase().trim());
        product.setName(dto.name().trim());
        product.setDescription(dto.description());
        product.setPrice(dto.price());
        product.setQuantityInStock(dto.quantityInStock());
        product.setMinStock(dto.minStock());
        product.setCategory(dto.category());
        if (dto.status() != null) {
            product.setStatus(dto.status());
        }

        Product updated = repository.save(product);
        return ProductResponseDTO.fromEntity(updated);
    }

    @Transactional
    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Product with ID " + id + " not found.");
        }
        repository.deleteById(id);
    }

    @Transactional
    public ProductResponseDTO adjustStock(Long id, Integer quantityDelta) {
        Product product = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product with ID " + id + " not found."));

        int newQuantity = product.getQuantityInStock() + quantityDelta;
        if (newQuantity < 0) {
            throw new BusinessException("Insufficient stock. Current stock: " + product.getQuantityInStock());
        }

        product.setQuantityInStock(newQuantity);
        product.updateStatusBasedOnStock();

        Product updated = repository.save(product);
        return ProductResponseDTO.fromEntity(updated);
    }
}
