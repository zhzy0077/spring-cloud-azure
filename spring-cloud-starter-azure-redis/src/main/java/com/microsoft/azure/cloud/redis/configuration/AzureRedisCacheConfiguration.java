package com.microsoft.azure.cloud.redis.configuration;

import com.microsoft.azure.cloud.core.context.AzureContext;
import com.microsoft.azure.cloud.redis.AzureRedisCacheConnectionFactory;
import com.microsoft.azure.cloud.redis.properties.AzureRedisProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

@EnableCaching
@Configuration
@EnableConfigurationProperties(AzureRedisProperties.class)
public class AzureRedisCacheConfiguration {
    @Bean
    public RedisConnectionFactory connectionFactory(AzureContext azureContext, AzureRedisProperties properties) {
        return new AzureRedisCacheConnectionFactory(azureContext, properties.getName());
    }

    @Bean
    public RedisTemplate<?, ?> redisTemplate(AzureRedisCacheConnectionFactory connectionFactory) {
        RedisTemplate<?, ?> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(connectionFactory);
        return redisTemplate;
    }

    @Bean
    public RedisCacheManager cacheManager(RedisTemplate<?, ?> redisTemplate) {
        return new RedisCacheManager(redisTemplate);
    }
}
