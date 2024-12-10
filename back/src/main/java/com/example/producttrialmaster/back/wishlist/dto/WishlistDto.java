package com.example.producttrialmaster.back.wishlist.dto;

import com.example.producttrialmaster.back.product.dto.ProductDto;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class WishlistDto {
    private List<ProductDto> products;

    public WishlistDto(List<ProductDto> products) {
        this.products = products;
    }
}
