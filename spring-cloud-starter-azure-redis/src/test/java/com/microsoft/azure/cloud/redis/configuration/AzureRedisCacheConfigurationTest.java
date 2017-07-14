package com.microsoft.azure.cloud.redis.configuration;

import com.microsoft.azure.cloud.core.context.AzureContext;
import com.microsoft.azure.cloud.redis.AzureRedisCacheConnectionFactory;
import com.microsoft.azure.cloud.redis.properties.AzureRedisProperties;
import com.microsoft.azure.management.redis.implementation.RedisManager;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = AzureRedisCacheConfigurationTest.Application.class)
public class AzureRedisCacheConfigurationTest {
    @Autowired
    private Service service;
    @Autowired
    private AzureRedisCacheConnectionFactory connectionFactory;
    @Autowired
    private CacheManager redisManager;
    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Before
    public void setUp() throws Exception {
        connectionFactory.getConnection().flushDb();
    }

    @Test
    public void getConnection() throws Exception {
        RedisConnection connection = connectionFactory.getConnection();
        Assert.assertNotNull(connection);
        connection.set("hello".getBytes(), "world".getBytes());
        Assert.assertEquals("world", new String(connection.get("hello".getBytes())));
    }

    @Test
    public void redisTemplate() throws Exception {
        redisTemplate.boundValueOps("hello").set("world");
        Assert.assertEquals(redisTemplate.boundValueOps("hello").get(), "world");
    }

    @Test
    public void testCacheable() throws Exception {
        int res = service.work(1);
        Assert.assertEquals(1, res);
        Assert.assertEquals(1, Service.hitCount);
        res = service.work(1);
        Assert.assertEquals(1, res);
        Assert.assertEquals(1, Service.hitCount); // Won't increase
    }

    @SpringBootApplication
    @EnableAzureRedisCache
    static class Application {

        @Bean
        public Service service() {
            return new Service();
        }
    }

    @Component
    static class Service {
        public static int hitCount = 0;

        @Cacheable("work")
        public int work(int v) {
            hitCount++;
            return v;
        }
    }

}