package com.dudka.store.serviceTest;

import com.dudka.store.entity.Product;
import com.dudka.store.service.ProductService;
import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.Level;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
@Log4j2
@Sql(scripts = "/init.sql")
public class ProductServiceTest {

    @Autowired
    private ProductService productServiceImpl;


    @Test
    public void testFindAllProducts () {
        List<Product> productList = new ArrayList<>();
        productList.add(new Product(1L, "ipone 11", 20000.99, true));
        productList.add(new Product(2L, "iphone 12", 30000.99, true));
        List<Product> list = productServiceImpl.findAll();
        log.log(Level.INFO, list);
        assertEquals(productList, productServiceImpl.findAll());
    }

    @Test
    public void testSaveProducts() {
        Product productToSave = new Product("iphone 14", 49999.9, true);
        assertEquals(Optional.of(productServiceImpl.saveItem(productToSave)), productServiceImpl.findById(3L));
    }

}
