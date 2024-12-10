package com.example.producttrialmaster.back.cart.dto;

import com.example.producttrialmaster.back.product.enumeration.InventoryStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartItemDto {

    private Long productId;
    private String productName;
    private double productPrice;
    private InventoryStatus inventoryStatus;
    private Integer rating;
    private int quantity;
    private String image;


}
