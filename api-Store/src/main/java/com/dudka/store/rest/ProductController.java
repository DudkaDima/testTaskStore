package com.dudka.store.rest;

import com.dudka.store.entity.Product;
import com.dudka.store.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/product")
public class ProductController {

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @RequestMapping(method = RequestMethod.GET, consumes = MediaType.ALL_VALUE, path = "/getAllProducts")
    @ResponseBody
    public ResponseEntity<List<Product>> findAllProducts() {
        return ResponseEntity.ok(productService.findAll());
    }

    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.ALL_VALUE, path = "/saveProduct")
    @ResponseBody
    public void saveOrder(@RequestBody Product product) {
        productService.saveItem(product);
    }
}
