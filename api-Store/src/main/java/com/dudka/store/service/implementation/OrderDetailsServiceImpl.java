package com.dudka.store.service.implementation;

import com.dudka.store.entity.OrderDetails;
import com.dudka.store.repository.OrderDetailsRepository;
import com.dudka.store.service.OrderDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class OrderDetailsServiceImpl implements OrderDetailsService {

    private final OrderDetailsRepository orderDetailsRepository;


    @Autowired
    public OrderDetailsServiceImpl(OrderDetailsRepository orderDetailsRepository) {
        this.orderDetailsRepository = orderDetailsRepository;
    }


    @Override
    @Transactional
    public List<OrderDetails> finaAll() {
        return orderDetailsRepository.findAll();
    }

    @Override
    @Transactional
    public OrderDetails saveOrderDetails(OrderDetails orderDetails) {
        return orderDetailsRepository.save(orderDetails);
    }

    @Override
    @Transactional
    public void removeOrderById(Long orderDetailsId) {
        orderDetailsRepository.deleteById(orderDetailsId);
    }

    @Override
    @Transactional
    public Optional<OrderDetails> findById(Long id) {
        return orderDetailsRepository.findById(id);
    }


}
