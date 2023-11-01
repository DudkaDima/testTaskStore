package com.dudka.store.dto;

import com.dudka.store.entity.Product;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class OrderRetrieveDto  {

    public OrderRetrieveDto(Product product, Long quantity) {
        this.product = new ProductRetrieveDto(product);
        this.quantity = quantity;
    }

    private ProductRetrieveDto product;

    private Long quantity;

}
