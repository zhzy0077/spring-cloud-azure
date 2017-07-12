package com.microsoft.azure.cloud.core.context;

import com.microsoft.azure.PagedList;
import com.microsoft.azure.cloud.core.exception.NonUniqueResultException;
import com.microsoft.azure.management.Azure;
import com.microsoft.azure.management.redis.RedisCache;

/**
 * Created by t-zhzhe on 7/11/2017 in spring-cloud-azure.
 */
public class AzureContext {
    private final Azure azure;
    private String resourceGroup;

    public AzureContext(Azure azure) {
        this.azure = azure;
    }

    public AzureContext(Azure azure, String resourceGroup) {
        this.azure = azure;
        this.resourceGroup = resourceGroup;
    }

    /**
     * Find RedisCache in this AzureContext, assuming only one RedisCache exist in resource group.
     * @return RedisCache found.
     */
    public RedisCache redisCache() {
        PagedList<RedisCache> redisCaches = azure.redisCaches().listByResourceGroup(resourceGroup);
        if (redisCaches.size() > 1) {
            throw new NonUniqueResultException("RedisCache");
        }
        return redisCaches.get(0);
    }

    /**
     * Find RedisCache in this AzureContext.
     * @param redisName RedisCache's name
     * @return RedisCache found.
     */
    public RedisCache redisCache(String redisName) {
        return azure.redisCaches().getByResourceGroup(resourceGroup, redisName);
    }

}
