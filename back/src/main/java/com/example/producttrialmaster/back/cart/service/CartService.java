package com.example.producttrialmaster.back.cart.service;

import com.example.producttrialmaster.back.account.entity.User;
import com.example.producttrialmaster.back.cart.dto.CartDto;
import com.example.producttrialmaster.back.cart.entity.Cart;

public interface CartService {

    CartDto getCartForUser(String email);

    Cart createNewCartForUser(User user);

    CartDto addProductToCart(String email, Long productId, int quantity);

    CartDto removeProductFromCart(String email, Long productId);

}
