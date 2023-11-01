package com.dudka.store.serviceTest;

import com.dudka.store.dto.OrderDto;
import com.dudka.store.dto.OrderRequestDto;
import com.dudka.store.entity.OrderDetails;
import com.dudka.store.entity.Product;
import com.dudka.store.entity.User;
import com.dudka.store.repository.OrderDetailsRepository;
import com.dudka.store.service.OrderDetailsService;
import com.dudka.store.service.ProductService;
import com.dudka.store.service.UserService;
import com.dudka.store.util.mapper.MapperEntityDto;
import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.Level;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
@Log4j2
@Sql(scripts = "/init.sql")
public class OrderServiceTest {

    @Autowired
    private OrderDetailsService orderDetailsService;

    @Autowired
    private UserService userService;

    @Autowired
    private ProductService productService;

    @Test
    void testSaveOrderDetailsMethod() {
        List<OrderDto> orderDtos = new ArrayList<>();
        orderDtos.add(new OrderDto(1L, 5L));
        OrderRequestDto orderRequestDto =
                new OrderRequestDto(true, 1L, orderDtos);

        Optional<User> user =
                userService.findUserById(orderRequestDto.getUserId());
        List<Product> productList =
                productService.findAllProductsById(
                        MapperEntityDto.getAllProductsId(
                                orderRequestDto.getOrder()));
        OrderDetails orderDetails = MapperEntityDto.mapOrderRequestToOrderDetails(
                orderRequestDto, user.get(), productList);

        Assertions.assertEquals(orderDetailsService.saveOrderDetails(orderDetails),
                orderDetailsService.findById(1L).get());
    }

}
