package com.dudka.store.service;

import com.dudka.store.entity.Product;

import java.util.List;
import java.util.Optional;

public interface ProductService {

    List<Product> findAll();

    Product saveItem(Product product);

    Optional<Product> findById(Long id);

    List<Product> findAllProductsById(List<Long> listOfId);

}
