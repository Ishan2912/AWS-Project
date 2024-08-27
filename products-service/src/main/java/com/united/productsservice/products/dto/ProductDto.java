package com.united.productsservice.products.dto;

import com.united.productsservice.products.model.Product;

public record ProductDto(
        String id, String name, String code, float price, String model
) {
    public ProductDto(Product product){
        this(product.getId(), product.getProductName(), product.getCode(), product.getPrice(), product.getModel());
    }

    public static Product toProduct(ProductDto dto){
        return new Product(dto.id, dto.name, dto.code, dto.price, dto.model);
    }
}
