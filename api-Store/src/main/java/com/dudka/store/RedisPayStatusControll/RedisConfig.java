package com.dudka.store.RedisPayStatusControll;

import com.dudka.store.service.OrderDetailsService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;

@Configuration
@EnableRedisRepositories
@Log4j2
public class RedisConfig {

    private final OrderDetailsService orderDetailsService;

    private final RedisConnectionFactory redisConnectionFactory;

    @Autowired
    public RedisConfig(OrderDetailsService orderDetailsService, RedisConnectionFactory redisConnectionFactory) {
        this.orderDetailsService = orderDetailsService;
        this.redisConnectionFactory = redisConnectionFactory;
    }

    @Bean
    RedisMessageListenerContainer container() {
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(redisConnectionFactory);
        // subscribe to Redis key expiration events
        container.addMessageListener(new MessageListener() {
            @Override
            public void onMessage(Message message, byte[] pattern) {
                String expiredKey = message.toString();
                if (expiredKey.startsWith("order:")) {
                    // processing logic for expired orders
                    String orderId = expiredKey.split(":")[1];
                    if (!orderDetailsService.findById(Long.valueOf(orderId)).get().getPayment_status()) {
                        orderDetailsService.removeOrderById(Long.valueOf(orderId));
                    }
                }
            }
        }, new PatternTopic("__keyevent@*__:expired"));
        return container;
    }
}
