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
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/api/v1/order")
@Log4j2
public class OrderController {

    private final RedisTemplate<String, OrderRequestDto> redisTemplate;
    private final UserService userService;

    private final OrderDetailsService orderDetailsService;

    private final ProductService productService;



    @Autowired
    public OrderController(RedisTemplate<String, OrderRequestDto> redisTemplate, UserService userService, OrderDetailsService orderDetailsService,
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
    @ResponseBody()
    public ResponseEntity<String> saveOrder(@RequestBody OrderRequestDto orderRequestDto) {
        String uuid = UUID.randomUUID().toString();
        redisTemplate.opsForValue().set(uuid, orderRequestDto);
        redisTemplate.expire(uuid, 20, TimeUnit.SECONDS);
        return ResponseEntity.ok(uuid);
    }

    @RequestMapping(method = RequestMethod.PATCH, consumes = MediaType.ALL_VALUE, path = "/payForOrder")
    @ResponseBody
    public void payForOrder(@RequestParam(name = "id") String id) {
        OrderRequestDto orderRequestDto = redisTemplate.opsForValue().get(id);
        if(orderRequestDto != null) {
            orderRequestDto.setPaymentStatus(true);
            Optional<User> user =
                    userService.findUserById(orderRequestDto.getUserId());
            List<Product> productList =
                    productService.findAllProductsById(
                            MapperEntityDto.getAllProductsId(
                                    orderRequestDto.getOrder()));
            OrderDetails orderDetails = MapperEntityDto.mapOrderRequestToOrderDetails(
                    orderRequestDto, user.get(), productList);
            orderDetailsService.saveOrderDetails(orderDetails);
        }

    }
}
