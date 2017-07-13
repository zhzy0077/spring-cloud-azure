package com.microsoft.azure.cloud.core.configuration;

import com.microsoft.azure.cloud.core.authentication.Credentials;
import com.microsoft.azure.cloud.core.context.AzureContextFactoryBean;
import com.microsoft.azure.cloud.core.properties.AzureContextProperties;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@AutoConfigureAfter(CredentialConfiguration.class)
public class AzureContextConfiguration {
    @Bean
    public AzureContextFactoryBean factoryBean(AzureContextProperties properties, Credentials credentials) {
        return new AzureContextFactoryBean(credentials,
                properties.getSubscriptionId(), properties.getResourceGroup());
    }

}
