package com.dudka.store.util.mapper;

import com.dudka.store.dto.OrderDto;
import com.dudka.store.dto.OrderRequestDto;
import com.dudka.store.entity.Order;
import com.dudka.store.entity.OrderDetails;
import com.dudka.store.entity.Product;
import com.dudka.store.entity.User;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public abstract class MapperEntityDto {
    public static OrderDetails mapOrderRequestToOrderDetails(
            OrderRequestDto orderRequestDto, User user, List<Product> productList) {
        return new OrderDetails(orderRequestDto.getPaymentStatus(),
                user, orderDtoToOrder(orderRequestDto.getOrder(), productList));
    }

    public static List<Order> orderDtoToOrder(List<OrderDto> orderDtoList, List<Product> productList) {

        List<Order> orders = new ArrayList<>();
        for(int i = 0; i < orderDtoList.size(); i++) {
            orders.add(new Order(productList.get(i), orderDtoList.get(i).getQuantity()));
        }

        return orders;
    }

    public static List<Long> getAllProductsId(List<OrderDto> orderDtoList) {
        return orderDtoList.stream().map(OrderDto::getProductId).collect(Collectors.toList());
    }




}
