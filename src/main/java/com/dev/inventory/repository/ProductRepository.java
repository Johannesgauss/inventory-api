package com.dev.inventory.repository;

import com.dev.inventory.model.Product;
import com.dev.inventory.model.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    Optional<Product> findBySku(String sku);

    boolean existsBySku(String sku);

    boolean existsBySkuAndIdNot(String sku, Long id);

    List<Product> findByCategory(ProductCategory category);

    @Query("SELECT p FROM Product p WHERE LOWER(p.name) LIKE LOWER(CONCAT('%', :termo, '%')) " +
           "OR LOWER(p.sku) LIKE LOWER(CONCAT('%', :termo, '%')) " +
           "OR LOWER(p.description) LIKE LOWER(CONCAT('%', :termo, '%'))")
    List<Product> search(@Param("termo") String termo);
}
