package com.microsoft.azure.cloud.core.authentication;

import com.microsoft.azure.AzureEnvironment;
import com.microsoft.azure.credentials.ApplicationTokenCredentials;
import com.microsoft.azure.management.Azure;

import java.io.IOException;


public class ServicePrincipalCredentials implements Credentials {
    private final ApplicationTokenCredentials credentials;

    public ServicePrincipalCredentials(ApplicationTokenCredentials credentials) {
        this.credentials = credentials;
    }

    public ServicePrincipalCredentials(String clientId, String domain, String secret) {
        this.credentials = new ApplicationTokenCredentials(clientId, domain, secret, null);
    }

    public ServicePrincipalCredentials(String clientId, String domain, String secret, AzureEnvironment environment) {
        this.credentials = new ApplicationTokenCredentials(clientId, domain, secret, environment);
    }

    @Override
    public Azure.Authenticated authenticate() throws IOException {
        return Azure.authenticate(credentials);
    }
}
