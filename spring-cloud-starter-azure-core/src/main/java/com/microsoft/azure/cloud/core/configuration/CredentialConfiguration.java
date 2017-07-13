package com.microsoft.azure.cloud.core.configuration;

import com.microsoft.azure.cloud.core.authentication.Credentials;
import com.microsoft.azure.cloud.core.authentication.FileCredentials;
import com.microsoft.azure.cloud.core.authentication.ServicePrincipalCredentials;
import com.microsoft.azure.cloud.core.properties.AzureContextProperties;
import com.microsoft.azure.cloud.core.properties.CredentialProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties({CredentialProperties.class, AzureContextProperties.class})
public class CredentialConfiguration {
    @Bean
    public Credentials credentials(CredentialProperties properties) {
        if (properties.getAuthenticationMode() ==
                CredentialProperties.AuthenticationMode.SERVICE_PRINCIPAL) {
            return new ServicePrincipalCredentials(properties.getClientId(),
                    properties.getDomain(), properties.getClientSecret());
        } else {
            return new FileCredentials(properties.getAuthFile());
        }
    }
}
