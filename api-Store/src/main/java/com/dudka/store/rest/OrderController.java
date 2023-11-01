package com.dudka.store.rest;

import com.dudka.store.dto.AdminOrderDetailsRetrieveDto;
import com.dudka.store.dto.OrderRequestDto;
import com.dudka.store.entity.OrderDetails;
import com.dudka.store.entity.Product;
import com.dudka.store.entity.User;
import com.dudka.store.service.OrderDetailsService;
import com.dudka.store.service.ProductService;
import com.dudka.store.service.UserService;
import com.dudka.store.util.mapper.MapperEntityDto;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/api/v1/order")
@Log4j2
public class OrderController {

    
    private final StringRedisTemplate redisTemplate;
    private final UserService userService;

    private final OrderDetailsService orderDetailsService;

    private final ProductService productService;



    @Autowired
    public OrderController(StringRedisTemplate redisTemplate, UserService userService, OrderDetailsService orderDetailsService,
                           ProductService productService) {
        this.redisTemplate = redisTemplate;
        this.userService = userService;
        this.productService = productService;
        this.orderDetailsService = orderDetailsService;

    }

    @RequestMapping(method = RequestMethod.GET, consumes = MediaType.ALL_VALUE, path = "/getOrders")
    @ResponseBody
    public ResponseEntity<List<AdminOrderDetailsRetrieveDto>> getOrder() {
        return ResponseEntity.ok(orderDetailsService.finaAll().stream()
                .map(AdminOrderDetailsRetrieveDto::new)
                .collect(Collectors.toList()));
    }

    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.ALL_VALUE, path = "/saveOrder")
    @ResponseBody
    public void saveOrder(@RequestBody OrderRequestDto orderRequestDto) {
        Optional<User> user =
                userService.findUserById(orderRequestDto.getUserId());
        List<Product> productList =
                productService.findAllProductsById(
                        MapperEntityDto.getAllProductsId(
                                orderRequestDto.getOrder()));
        OrderDetails orderDetails = MapperEntityDto.mapOrderRequestToOrderDetails(
                orderRequestDto, user.get(), productList);
        OrderDetails savedOrderDetails = orderDetailsService.saveOrderDetails(orderDetails);
        redisTemplate.opsForValue().set("order:" + savedOrderDetails.getUser().getId(),
                String.valueOf(savedOrderDetails.getUser().getId()),
                30, TimeUnit.SECONDS);
    }

    @RequestMapping(method = RequestMethod.PATCH, consumes = MediaType.ALL_VALUE, path = "/payForOrder")
    @ResponseBody
    public void payForOrder(@RequestParam(name = "id") Long id) {
        Optional<OrderDetails> orderDetails = orderDetailsService.findById(id);
        if(orderDetails.isPresent()) {
            orderDetails.get().setPayment_status(true);
            orderDetailsService.saveOrderDetails(orderDetails.get());
        }
    }
}
