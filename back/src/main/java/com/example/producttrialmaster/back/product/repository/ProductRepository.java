package com.example.producttrialmaster.back.product.repository;

import com.example.producttrialmaster.back.product.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
    boolean existsByCode(String code);
}
