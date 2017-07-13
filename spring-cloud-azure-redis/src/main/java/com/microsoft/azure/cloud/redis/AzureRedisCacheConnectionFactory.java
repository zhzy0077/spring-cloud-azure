package com.microsoft.azure.cloud.redis;

import com.microsoft.azure.cloud.core.context.AzureContext;
import com.microsoft.azure.management.redis.RedisCache;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisClusterConnection;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisSentinelConnection;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import redis.clients.jedis.JedisShardInfo;

public class AzureRedisCacheConnectionFactory implements RedisConnectionFactory, InitializingBean, DisposableBean {
    private JedisConnectionFactory connectionFactory;
    private AzureContext azureContext;
    private String redisName;

    public AzureRedisCacheConnectionFactory(AzureContext azureContext) {
        this.azureContext = azureContext;
    }

    public AzureRedisCacheConnectionFactory(AzureContext azureContext, String redisName) {
        this.azureContext = azureContext;
        this.redisName = redisName;
    }

    @Override
    public void destroy() throws Exception {
        connectionFactory.destroy();
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        RedisCache redisCache;
        if (redisName == null) {
            redisCache = azureContext.redisCache();
        } else {
            redisCache = azureContext.redisCache(redisName);
        }
        JedisShardInfo info = new JedisShardInfo(
                redisCache.hostName(),
                redisCache.port(),
                false
        );
        info.setPassword(redisCache.getKeys().primaryKey());
        connectionFactory = new JedisConnectionFactory(info);
        connectionFactory.setUsePool(true);
        connectionFactory.afterPropertiesSet();
    }

    @Override
    public RedisConnection getConnection() {
        return connectionFactory.getConnection();
    }

    @Override
    public RedisClusterConnection getClusterConnection() {
        return connectionFactory.getClusterConnection();
    }

    @Override
    public boolean getConvertPipelineAndTxResults() {
        return connectionFactory.getConvertPipelineAndTxResults();
    }

    @Override
    public RedisSentinelConnection getSentinelConnection() {
        return connectionFactory.getSentinelConnection();
    }

    @Override
    public DataAccessException translateExceptionIfPossible(RuntimeException ex) {
        return connectionFactory.translateExceptionIfPossible(ex);
    }
}
