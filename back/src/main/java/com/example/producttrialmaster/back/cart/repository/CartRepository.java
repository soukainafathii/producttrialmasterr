package com.example.producttrialmaster.back.cart.repository;

import com.example.producttrialmaster.back.account.entity.User;
import com.example.producttrialmaster.back.cart.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
    Optional<Cart> findByUser(User user);
}
