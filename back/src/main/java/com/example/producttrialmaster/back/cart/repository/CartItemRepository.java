package com.example.producttrialmaster.back.cart.repository;

import com.example.producttrialmaster.back.cart.entity.CartItem;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {

    @Transactional
    void deleteByProductId(@Param("productId") Long productId);

    boolean existsByProductId(@Param("productId") Long productId);

}

