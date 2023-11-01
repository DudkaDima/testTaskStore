package com.dudka.store.RedisPayStatusControll;

import com.dudka.store.dto.OrderRequestDto;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
@EnableRedisRepositories
@Log4j2
public class RedisConfig {

    private final RedisConnectionFactory redisConnectionFactory;

    @Autowired
    public RedisConfig(RedisConnectionFactory redisConnectionFactory) {
        this.redisConnectionFactory = redisConnectionFactory;
    }
    @Bean
    public RedisTemplate<String, OrderRequestDto> redisTemplate() {
        RedisTemplate<String, OrderRequestDto> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory);

        // Set up serializers for key and value
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new Jackson2JsonRedisSerializer<>(OrderRequestDto.class));

        // Use the same serializers for hash key and hash value
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashValueSerializer(new Jackson2JsonRedisSerializer<>(OrderRequestDto.class));

        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }
}
