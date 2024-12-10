package com.example.producttrialmaster.back.product.dto;

import com.example.producttrialmaster.back.product.enumeration.InventoryStatus;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProductDto {
        private Long id;
        private String code;
        private String name;
        private String description;
        private String image;
        private String category;
        private Double price;
        private Integer quantity;
        private String internalReference;
        private Long shellId;
        private InventoryStatus inventoryStatus;
        private Integer rating;
        private String createdAt;
        private String updatedAt;

        public ProductDto(Long id, String name, Double price, String description, Integer rating, String image) {
                this.id = id;
                this.name = name;
                this.price = price;
                this.description = description;
                this.rating = rating;
                this.image = image;
        }

}
