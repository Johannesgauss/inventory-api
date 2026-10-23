package com.dev.inventory.service;

import com.dev.inventory.dto.ProductRequestDTO;
import com.dev.inventory.dto.ProductResponseDTO;
import com.dev.inventory.exception.BusinessException;
import com.dev.inventory.model.Product;
import com.dev.inventory.model.ProductCategory;
import com.dev.inventory.model.ProductStatus;
import com.dev.inventory.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    private ProductRepository repository;

    @InjectMocks
    private ProductService service;

    private Product product;
    private ProductRequestDTO requestDTO;

    @BeforeEach
    void setUp() {
        product = Product.builder()
                .id(1L)
                .sku("LOG-900")
                .name("Mouse Sem Fio MX Master 3S")
                .description("Mouse ergonômico de alta precisão")
                .price(new BigDecimal("550.00"))
                .quantityInStock(12)
                .minStock(3)
                .category(ProductCategory.PERIFERICOS)
                .status(ProductStatus.ACTIVE)
                .build();

        requestDTO = new ProductRequestDTO(
                "LOG-900",
                "Mouse Sem Fio MX Master 3S",
                "Mouse ergonômico de alta precisão",
                new BigDecimal("550.00"),
                12,
                3,
                ProductCategory.PERIFERICOS,
                ProductStatus.ACTIVE
        );
    }

    @Test
    @DisplayName("Should create product successfully")
    void shouldCreateProductSuccessfully() {
        when(repository.existsBySku(requestDTO.sku())).thenReturn(false);
        when(repository.save(any(Product.class))).thenReturn(product);

        ProductResponseDTO result = service.create(requestDTO);

        assertNotNull(result);
        assertEquals(product.getName(), result.name());
        verify(repository, times(1)).save(any(Product.class));
    }

    @Test
    @DisplayName("Should throw BusinessException when SKU already exists")
    void shouldThrowExceptionWhenSkuDuplicated() {
        when(repository.existsBySku(requestDTO.sku())).thenReturn(true);

        assertThrows(BusinessException.class, () -> service.create(requestDTO));
        verify(repository, never()).save(any(Product.class));
    }

    @Test
    @DisplayName("Should adjust stock correctly and update status to LOW_STOCK")
    void shouldAdjustStockCorrectly() {
        when(repository.findById(1L)).thenReturn(Optional.of(product));
        when(repository.save(any(Product.class))).thenAnswer(invocation -> invocation.getArgument(0));

        ProductResponseDTO result = service.adjustStock(1L, -10);

        assertEquals(2, result.quantityInStock());
        assertEquals(ProductStatus.LOW_STOCK, result.status());
    }
}
