package com.dev.inventory.dto;

import com.dev.inventory.model.ProductCategory;
import com.dev.inventory.model.ProductStatus;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public record ProductRequestDTO(
        @NotBlank(message = "O código SKU é obrigatório.")
        @Size(min = 3, max = 50, message = "O SKU deve conter entre 3 e 50 caracteres.")
        String sku,

        @NotBlank(message = "O nome do produto é obrigatório.")
        @Size(min = 3, max = 150, message = "O nome deve conter entre 3 e 150 caracteres.")
        String name,

        String description,

        @NotNull(message = "O preço é obrigatório.")
        @DecimalMin(value = "0.01", message = "O preço unitário deve ser maior que zero.")
        BigDecimal price,

        @NotNull(message = "A quantidade em estoque é obrigatória.")
        @Min(value = 0, message = "A quantidade em estoque não pode ser negativa.")
        Integer quantityInStock,

        @NotNull(message = "O estoque mínimo é obrigatório.")
        @Min(value = 0, message = "O estoque mínimo não pode ser negativo.")
        Integer minStock,

        @NotNull(message = "A categoria do produto é obrigatória.")
        ProductCategory category,

        ProductStatus status
) {}
