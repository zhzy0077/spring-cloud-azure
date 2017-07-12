package com.microsoft.azure.cloud.redis;

import com.microsoft.azure.cloud.core.authentication.Credentials;
import com.microsoft.azure.cloud.core.authentication.ServicePrincipalCredentials;
import com.microsoft.azure.cloud.core.context.AzureContext;
import com.microsoft.azure.cloud.core.context.AzureContextFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by t-zhzhe on 7/11/2017 in spring-cloud-azure.
 */
@Configuration
public class AzureTestContextConfiguration {
    @Bean
    public AzureContextFactoryBean azureContextFactoryBean(Credentials credentials) {
        return new AzureContextFactoryBean(credentials, "2085065b-00f8-4cba-9675-ba15f4d4ab66", "redis");
    }

    @Bean
    public Credentials principalCredentials() {
        return new ServicePrincipalCredentials("ba3a3856-0e2d-489f-95ac-34732a97ae0b",
                "microsoft.com",
                "bbc8f798-9411-49eb-8515-a5f0686b90f7");
    }
}
