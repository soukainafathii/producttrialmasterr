package com.example.producttrialmaster.back.cart.service.impl;

import com.example.producttrialmaster.back.account.entity.User;
import com.example.producttrialmaster.back.account.repository.UserRepository;
import com.example.producttrialmaster.back.cart.dto.CartDto;
import com.example.producttrialmaster.back.cart.dto.CartItemDto;
import com.example.producttrialmaster.back.cart.entity.Cart;
import com.example.producttrialmaster.back.cart.entity.CartItem;
import com.example.producttrialmaster.back.cart.repository.CartRepository;
import com.example.producttrialmaster.back.cart.service.CartService;
import com.example.producttrialmaster.back.exception.ResourceNotFoundException;
import com.example.producttrialmaster.back.product.entity.Product;
import com.example.producttrialmaster.back.product.repository.ProductRepository;

import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    public CartServiceImpl(CartRepository cartRepository, ProductRepository productRepository, UserRepository userRepository) {
        this.cartRepository = cartRepository;
        this.productRepository = productRepository;
        this.userRepository = userRepository;
    }

    @Override
    public CartDto getCartForUser(String email) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        Cart cart = cartRepository.findByUser(user)
                .orElse(null);

        if (cart == null || cart.getItems().isEmpty()) {
            return new CartDto(List.of());
        }

        List<CartItemDto> cartItemDtos = cart.getItems().stream()
                .map(item -> new CartItemDto(
                        item.getProduct().getId(),
                        item.getProduct().getName(),
                        item.getProduct().getPrice(),
                        item.getProduct().getInventoryStatus(),
                        item.getProduct().getRating(),
                        item.getQuantity(),
                        item.getProduct().getImage()))
                .toList();

        return new CartDto(cartItemDtos);
    }


    @Override
    public Cart createNewCartForUser(User user) {
        Cart cart = new Cart();
        cart.setUser(user);
        return cartRepository.save(cart);
    }

    @Override
    public CartDto addProductToCart(String email, Long productId, int quantity) {
        Cart cart = cartRepository.findByUser(userRepository.findByEmail(email)
                        .orElseThrow(() -> new ResourceNotFoundException("User not found")))
                .orElseGet(() -> createNewCartForUser(userRepository.findByEmail(email).orElseThrow()));

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));

        CartItem cartItem = cart.getItems().stream()
                .filter(item -> item.getProduct().getId().equals(productId))
                .findFirst()
                .orElse(new CartItem());

        if (cartItem.getId() == null) {
            cartItem.setCart(cart);
            cartItem.setProduct(product);
            cartItem.setQuantity(quantity);
            cart.getItems().add(cartItem);
        } else {
            cartItem.setQuantity(cartItem.getQuantity() + quantity);
        }

        cartRepository.save(cart);

        return getCartForUser(email);
    }

    @Override
    public CartDto removeProductFromCart(String email, Long productId) {
        Cart cart = cartRepository.findByUser(userRepository.findByEmail(email)
                        .orElseThrow(() -> new RuntimeException("User not found")))
                .orElseThrow(() -> new RuntimeException("Cart not found"));

        CartItem cartItem = cart.getItems().stream()
                .filter(item -> item.getProduct().getId().equals(productId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Product not found in cart"));

        if (cartItem.getQuantity() > 1) {
            cartItem.setQuantity(cartItem.getQuantity() - 1);
        } else {
            cart.getItems().remove(cartItem);
        }

        cartRepository.save(cart);

        return getCartForUser(email);
    }
}
