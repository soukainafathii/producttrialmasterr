package com.example.producttrialmaster.back.product.service.impl;

import com.example.producttrialmaster.back.cart.repository.CartItemRepository;
import com.example.producttrialmaster.back.cart.repository.CartRepository;
import com.example.producttrialmaster.back.exception.DuplicateCodeException;
import com.example.producttrialmaster.back.exception.ResourceNotFoundException;
import com.example.producttrialmaster.back.product.dto.ProductDto;
import com.example.producttrialmaster.back.product.entity.Product;
import com.example.producttrialmaster.back.product.mapper.ProductMapper;
import com.example.producttrialmaster.back.product.repository.ProductRepository;
import com.example.producttrialmaster.back.product.service.ProductService;
import com.example.producttrialmaster.back.wishlist.repository.WishlistRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {


    private final ProductRepository productRepository;
    private final WishlistRepository wishlistRepository;
    private final CartItemRepository cartItemRepository;
    private final ProductMapper productMapper;

    public ProductServiceImpl(ProductRepository productRepository, WishlistRepository wishlistRepository, CartRepository cartRepository, CartItemRepository cartItemRepository, ProductMapper productMapper) {
        this.productRepository = productRepository;
        this.wishlistRepository = wishlistRepository;
        this.cartItemRepository = cartItemRepository;
        this.productMapper = productMapper;
    }

    @Override
    public List<ProductDto> getAllProducts() {
        return productRepository.findAll()
                .stream()
                .map(productMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public ProductDto getProductById(Long id) {
        var product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with ID: " + id));
        return productMapper.toDto(product);
    }

    @Override
    public ProductDto createProduct(ProductDto productDto) {

        if (productRepository.existsByCode(productDto.getCode())) {
            throw new DuplicateCodeException("The product code already exists.");
        }
        Product product = productMapper.toEntity(productDto);
        Product savedProduct = productRepository.save(product);
        return productMapper.toDto(savedProduct);
    }

    @Override
    public ProductDto updateProduct(Long id, ProductDto productDto) {
        var existingProduct = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found with ID: " + id));

        if (productDto.getCode() != null) {
            existingProduct.setCode(productDto.getCode());
        }
        if (productDto.getName() != null) {
            existingProduct.setName(productDto.getName());
        }
        if (productDto.getDescription() != null) {
            existingProduct.setDescription(productDto.getDescription());
        }
        if (productDto.getImage() != null) {
            existingProduct.setImage(productDto.getImage());
        }
        if (productDto.getCategory() != null) {
            existingProduct.setCategory(productDto.getCategory());
        }
        if (productDto.getPrice() != null) {
            existingProduct.setPrice(productDto.getPrice());
        }
        if (productDto.getQuantity() != null) {
            existingProduct.setQuantity(productDto.getQuantity());
        }
        if (productDto.getInternalReference() != null) {
            existingProduct.setInternalReference(productDto.getInternalReference());
        }
        if (productDto.getShellId() != null) {
            existingProduct.setShellId(productDto.getShellId());
        }
        if (productDto.getInventoryStatus() != null) {
            existingProduct.setInventoryStatus(productDto.getInventoryStatus());
        }
        if (productDto.getRating() != null) {
            existingProduct.setRating(productDto.getRating());
        }

        Product updatedProduct = productRepository.save(existingProduct);
        return productMapper.toDto(updatedProduct);
    }

    @Override
    public void deleteProduct(Long id) {

        boolean existsInWishlist = wishlistRepository.existsByProductsId(id);
        boolean existsIncCartItem = cartItemRepository.existsByProductId(id);

        if (existsInWishlist) {
            wishlistRepository.deleteByProductsId(id);
        }
        if (existsIncCartItem) {
            cartItemRepository.deleteByProductId(id);
        }

        if (!productRepository.existsById(id)) {
            throw new RuntimeException("Product not found with ID: " + id);
        }
        productRepository.deleteById(id);
    }
}
