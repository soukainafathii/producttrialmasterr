package com.example.producttrialmaster.back.wishlist.repository;

import com.example.producttrialmaster.back.account.entity.User;
import com.example.producttrialmaster.back.wishlist.entity.Wishlist;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface WishlistRepository extends JpaRepository<Wishlist, Long> {
    Optional<Wishlist> findByUser(User user);

    @Transactional
    void deleteByProductsId(@Param("productId") Long productId);

    boolean existsByProductsId(@Param("productId") Long productId);


}

