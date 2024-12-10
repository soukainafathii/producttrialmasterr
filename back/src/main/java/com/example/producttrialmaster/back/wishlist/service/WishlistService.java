package com.example.producttrialmaster.back.wishlist.service;

import com.example.producttrialmaster.back.wishlist.dto.WishlistDto;

public interface WishlistService {

    WishlistDto getWishlistForUser(String email);

    WishlistDto addProductToWishlist(String email, Long productId);

    WishlistDto removeProductFromWishlist(String email, Long productId);

}
