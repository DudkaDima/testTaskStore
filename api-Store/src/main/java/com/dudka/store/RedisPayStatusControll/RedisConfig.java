package com.dudka.store.RedisPayStatusControll;

import com.dudka.store.entity.OrderDetails;
import com.dudka.store.service.OrderDetailsService;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.Level;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.scheduling.annotation.Async;

import java.awt.print.Book;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

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
    public RedisTemplate<Message, String> redisTemplate(RedisConnectionFactory connectionFactory) {
        RedisTemplate<Message, String> template = new RedisTemplate<>();
        template.setConnectionFactory(connectionFactory);

        return template;
    }
    @Bean
    RedisMessageListenerContainer container() {
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(redisConnectionFactory);

        // subscribe to Redis key expiration events
        container.addMessageListener(new MessageListener() {
            @SneakyThrows
            @Override
            public void onMessage(Message message, byte[] pattern) {

                String expiredKey = message.toString();
                if (expiredKey.startsWith("order:")) {
                    // processing logic for expired orders
                    log.log(Level.INFO, message);
                    String orderId = expiredKey.split(":")[1];
                    log.log(Level.INFO, orderId);
                    Optional<OrderDetails> orderDetails = orderDetailsService.findById(Long.valueOf(orderId));
                    log.log(Level.INFO, orderDetails);
                    if (orderDetails.isPresent()) {
                        if(!orderDetails.get().getPayment_status()) {
                            orderDetailsService.removeOrderById(orderDetails.get().getId());
                            container.destroy();
                            redisTemplate(container.getConnectionFactory()).delete(message);
                        }
                    }
                }
            }
        }, new PatternTopic("__keyevent@*__:expired"));
        return container;
    }
}
