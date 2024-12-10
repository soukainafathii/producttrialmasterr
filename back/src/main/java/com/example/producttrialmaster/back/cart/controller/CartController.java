package com.example.producttrialmaster.back.cart.controller;

import com.example.producttrialmaster.back.cart.dto.CartDto;
import com.example.producttrialmaster.back.cart.service.CartService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart")
public class CartController {
    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping
    public ResponseEntity<CartDto> getCartItems(@RequestAttribute("user") String email) {
        return ResponseEntity.ok(cartService.getCartForUser(email));
    }

    @PostMapping("/add")
    public ResponseEntity<CartDto> addProductToCart(@RequestAttribute("user") String email,
                                                    @RequestParam Long productId,
                                                    @RequestParam int quantity) {
        return ResponseEntity.ok(cartService.addProductToCart(email, productId, quantity));
    }

    @DeleteMapping("/remove")
    public ResponseEntity<CartDto> removeProductFromCart(@RequestAttribute("user") String email,
                                                         @RequestParam Long productId) {
        return ResponseEntity.ok(cartService.removeProductFromCart(email, productId));
    }
}