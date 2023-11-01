package com.dudka.store.dto;

import com.dudka.store.entity.Product;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ProductRetrieveDto {

    public ProductRetrieveDto(Product product) {
        this.nameOfProduct = product.getNameOfProduct();
        this.price = product.getPrice();
        this.isAvailable = product.getIsAvailable();
    }

    private String nameOfProduct;

    private Double price;

    private Boolean isAvailable;
}
