package com.dev.inventory.controller;

import com.dev.inventory.dto.ProductRequestDTO;
import com.dev.inventory.dto.ProductResponseDTO;
import com.dev.inventory.model.ProductCategory;
import com.dev.inventory.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
@Tag(name = "Products", description = "Endpoints for Product Catalog and Stock Management")
public class ProductController {

    private final ProductService service;

    @GetMapping
    @Operation(summary = "List all products", description = "Returns products with optional search filter by name/SKU and category")
    public ResponseEntity<List<ProductResponseDTO>> findAll(
            @RequestParam(required = false) String query,
            @RequestParam(required = false) ProductCategory category) {
        return ResponseEntity.ok(service.findAll(query, category));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Find product by ID", description = "Retrieves full product details by its database ID")
    public ResponseEntity<ProductResponseDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping
    @Operation(summary = "Create product", description = "Creates a new catalog item validating SKU uniqueness")
    public ResponseEntity<ProductResponseDTO> create(@Valid @RequestBody ProductRequestDTO dto) {
        ProductResponseDTO created = service.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update product", description = "Updates an existing product item")
    public ResponseEntity<ProductResponseDTO> update(
            @PathVariable Long id,
            @Valid @RequestBody ProductRequestDTO dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete product", description = "Removes a product from the database")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/stock")
    @Operation(summary = "Adjust inventory stock", description = "Increments or decrements stock quantity by delta")
    public ResponseEntity<ProductResponseDTO> adjustStock(
            @PathVariable Long id,
            @RequestParam Integer delta) {
        return ResponseEntity.ok(service.adjustStock(id, delta));
    }
}
