package com.microsoft.azure.cloud.core.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = "azure", ignoreInvalidFields = true)
@Component
public class CredentialProperties {
    private AuthenticationMode authenticationMode;
    // Service Principal
    private String clientId;
    private String domain;
    private String clientSecret;
    // Auth File
    private String authFile;

    public AuthenticationMode getAuthenticationMode() {
        return authenticationMode;
    }

    public void setAuthenticationMode(AuthenticationMode authenticationMode) {
        this.authenticationMode = authenticationMode;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public void setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
    }

    public String getAuthFile() {
        return authFile;
    }

    public void setAuthFile(String authFile) {
        this.authFile = authFile;
    }

    public enum AuthenticationMode {
        SERVICE_PRINCIPAL,
        AUTHENTICATION_FILE;
    }
}
