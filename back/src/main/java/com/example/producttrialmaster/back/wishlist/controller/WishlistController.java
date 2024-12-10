package com.example.producttrialmaster.back.wishlist.controller;

import com.example.producttrialmaster.back.wishlist.dto.WishlistDto;
import com.example.producttrialmaster.back.wishlist.service.WishlistService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/wishlist")
public class WishlistController {

    private final WishlistService wishlistService;

    public WishlistController(WishlistService wishlistService) {
        this.wishlistService = wishlistService;
    }

    @GetMapping
    public ResponseEntity<WishlistDto> getWishlist(@RequestAttribute("user") String email) {
        return ResponseEntity.ok(wishlistService.getWishlistForUser(email));
    }

    @PostMapping("/add")
    public ResponseEntity<WishlistDto> addProductToWishlist(@RequestAttribute("user") String email,
                                                            @RequestParam Long productId) {
        return ResponseEntity.ok(wishlistService.addProductToWishlist(email, productId));
    }

    @DeleteMapping("/remove")
    public ResponseEntity<WishlistDto> removeProductFromWishlist(@RequestAttribute("user") String email,
                                                                 @RequestParam Long productId) {
        return ResponseEntity.ok(wishlistService.removeProductFromWishlist(email, productId));
    }
}
