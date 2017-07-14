package com.microsoft.azure.cloud.redis.configuration;

import com.microsoft.azure.cloud.redis.properties.AzureRedisProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Import;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Import(AzureRedisCacheConfiguration.class)
public @interface EnableAzureRedisCache {
}
