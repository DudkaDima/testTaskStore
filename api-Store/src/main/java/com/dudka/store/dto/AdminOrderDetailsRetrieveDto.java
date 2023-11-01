package com.dudka.store.dto;

import com.dudka.store.entity.OrderDetails;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
public class AdminOrderDetailsRetrieveDto {

    public AdminOrderDetailsRetrieveDto(OrderDetails orderDetails) {
        this.payment_status = orderDetails.getPayment_status();
        this.user = new UserRetrieveDto(orderDetails.getUser());
        this.orders = orderDetails.getOrders().
                stream().
                map(s-> new OrderRetrieveDto(s.getProduct(), s.getQuantity())).
                collect(Collectors.toList());
    }

    private Boolean payment_status;

    private UserRetrieveDto user;

    private List<OrderRetrieveDto> orders;


}
