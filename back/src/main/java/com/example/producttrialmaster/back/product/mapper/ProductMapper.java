package com.example.producttrialmaster.back.product.mapper;

import com.example.producttrialmaster.back.product.dto.ProductDto;
import com.example.producttrialmaster.back.product.entity.Product;
import com.example.producttrialmaster.back.product.utils.TimeFormatter;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    @Mapping(source = "createdAt", target = "createdAt", qualifiedByName = "formatTimestamp")
    @Mapping(source = "updatedAt", target = "updatedAt", qualifiedByName = "formatTimestamp")
    ProductDto toDto(Product product);

    @Mapping(source = "createdAt", target = "createdAt", qualifiedByName = "parseReadableDateToTimestamp")
    @Mapping(source = "updatedAt", target = "updatedAt", qualifiedByName = "parseReadableDateToTimestamp")
    Product toEntity(ProductDto productDto);

    @Named("formatTimestamp")
    static String formatTimestamp(Long timestamp) {
        return TimeFormatter.formatTimestamp(timestamp);
    }

    @Named("parseReadableDateToTimestamp")
    static Long parseReadableDateToTimestamp(String readableDate) {
        return TimeFormatter.parseReadableDateToTimestamp(readableDate);
    }
}