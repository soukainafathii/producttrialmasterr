package com.example.producttrialmaster.back.wishlist.service.impl;

import com.example.producttrialmaster.back.account.entity.User;
import com.example.producttrialmaster.back.account.repository.UserRepository;
import com.example.producttrialmaster.back.product.dto.ProductDto;
import com.example.producttrialmaster.back.product.entity.Product;
import com.example.producttrialmaster.back.product.repository.ProductRepository;
import com.example.producttrialmaster.back.wishlist.dto.WishlistDto;
import com.example.producttrialmaster.back.wishlist.entity.Wishlist;
import com.example.producttrialmaster.back.wishlist.repository.WishlistRepository;
import com.example.producttrialmaster.back.wishlist.service.WishlistService;

import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class WishlistServiceImpl implements WishlistService {

    private final WishlistRepository wishlistRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    public WishlistServiceImpl(WishlistRepository wishlistRepository, ProductRepository productRepository, UserRepository userRepository) {
        this.wishlistRepository = wishlistRepository;
        this.productRepository = productRepository;
        this.userRepository = userRepository;
    }

    @Override
    public WishlistDto getWishlistForUser(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Wishlist wishlist = wishlistRepository.findByUser(user)
                .orElseGet(() -> createWishlistForUser(user));

        return new WishlistDto(wishlist.getProducts().stream()
                .map(product -> new ProductDto(
                        product.getId(),
                        product.getName(),
                        product.getPrice(),
                        product.getDescription(),
                        product.getRating(),
                        product.getImage()
                ))
                .collect(Collectors.toList()));
    }


    @Override
    public WishlistDto addProductToWishlist(String email, Long productId) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        Wishlist wishlist = wishlistRepository.findByUser(user)
                .orElseGet(() -> createWishlistForUser(user));

        if (!wishlist.getProducts().contains(product)) {
            wishlist.getProducts().add(product);
        }

        wishlistRepository.save(wishlist);

        return getWishlistForUser(email);
    }

    @Override
    public WishlistDto removeProductFromWishlist(String email, Long productId) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Wishlist wishlist = wishlistRepository.findByUser(user)
                .orElseThrow(() -> new RuntimeException("Wishlist not found"));

        wishlist.getProducts().removeIf(product -> product.getId().equals(productId));

        wishlistRepository.save(wishlist);

        return getWishlistForUser(email);
    }

    private Wishlist createWishlistForUser(User user) {
        Wishlist wishlist = new Wishlist();
        wishlist.setUser(user);
        return wishlistRepository.save(wishlist);
    }
}
