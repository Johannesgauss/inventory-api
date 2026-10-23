package com.dev.inventory.dto;

import com.dev.inventory.model.Product;
import com.dev.inventory.model.ProductCategory;
import com.dev.inventory.model.ProductStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record ProductResponseDTO(
        Long id,
        String sku,
        String name,
        String description,
        BigDecimal price,
        Integer quantityInStock,
        Integer minStock,
        ProductCategory category,
        ProductStatus status,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
    public static ProductResponseDTO fromEntity(Product product) {
        return new ProductResponseDTO(
                product.getId(),
                product.getSku(),
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                product.getQuantityInStock(),
                product.getMinStock(),
                product.getCategory(),
                product.getStatus(),
                product.getCreatedAt(),
                product.getUpdatedAt()
        );
    }
}
