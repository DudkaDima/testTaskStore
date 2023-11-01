package com.dudka.store.service;

import com.dudka.store.entity.OrderDetails;

import java.util.List;
import java.util.Optional;

public interface OrderDetailsService {
    List<OrderDetails> finaAll();

    OrderDetails saveOrderDetails(OrderDetails orderDetails);

    void removeOrderById(Long orderDetailsId);

    Optional<OrderDetails> findById(Long id);

}


