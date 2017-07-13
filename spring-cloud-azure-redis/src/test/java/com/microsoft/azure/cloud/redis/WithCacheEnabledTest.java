package com.microsoft.azure.cloud.redis;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {AzureTestContextConfiguration.class, WithCacheEnabledTest.Config.class})
public class WithCacheEnabledTest {
    @Autowired
    private Service service;
    @Autowired
    private AzureRedisCacheConnectionFactory connectionFactory;

    @Before
    public void setUp() throws Exception {
        connectionFactory.getConnection().flushDb();
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

    static class Service {
        public static int hitCount = 0;

        @Cacheable("work")
        public int work(int v) {
            hitCount++;
            return v;
        }
    }

    @Configuration
    @EnableCaching
    static class Config {
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

        @Bean
        public Service service() {
            return new Service();
        }
    }
}
