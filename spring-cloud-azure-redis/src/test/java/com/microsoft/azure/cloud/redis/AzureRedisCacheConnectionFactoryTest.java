package com.microsoft.azure.cloud.redis;

import com.microsoft.azure.cloud.core.context.AzureContext;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by t-zhzhe on 7/11/2017 in spring-cloud-azure.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { AzureTestContextConfiguration.class, AzureRedisCacheConnectionFactoryTest.Config.class })
public class AzureRedisCacheConnectionFactoryTest {
    @Configuration
    static class Config {
        @Bean
        public RedisConnectionFactory connectionFactory(AzureContext azureContext) {
            return new AzureRedisCacheConnectionFactory(azureContext, "rereredis");
        }
    }

    @Autowired
    private RedisConnectionFactory connectionFactory;

    @Test
    public void connectionFactoryExist() throws Exception {
        Assert.assertNotNull(connectionFactory);
    }

    @Test
    public void getConnection() throws Exception {
        RedisConnection connection = connectionFactory.getConnection();
        Assert.assertNotNull(connection);
        connection.set("hello".getBytes(), "world".getBytes());
        Assert.assertEquals("world", new String(connection.get("hello".getBytes())));
    }


}