package com.microsoft.azure.cloud.core.context;

import com.microsoft.azure.cloud.core.authentication.Credentials;
import com.microsoft.azure.management.Azure;
import org.springframework.beans.factory.config.AbstractFactoryBean;
import org.springframework.util.Assert;

public class AzureContextFactoryBean extends AbstractFactoryBean<AzureContext> {
    private final Credentials credentials;
    private String resourceGroup;
    private String subscriptionId;

    public AzureContextFactoryBean(Credentials credentials) {
        Assert.notNull(credentials, "credentials must not be null");
        this.credentials = credentials;
    }

    public AzureContextFactoryBean(Credentials credentials, String subscriptionId) {
        this.credentials = credentials;
        this.subscriptionId = subscriptionId;
    }

    public AzureContextFactoryBean(Credentials credentials, String subscriptionId, String resourceGroup) {
        this.credentials = credentials;
        this.resourceGroup = resourceGroup;
        this.subscriptionId = subscriptionId;
    }

    public String getSubscriptionId() {
        return subscriptionId;
    }

    public void setSubscriptionId(String subscriptionId) {
        this.subscriptionId = subscriptionId;
    }

    public String getResourceGroup() {
        return resourceGroup;
    }

    public void setResourceGroup(String resourceGroup) {
        this.resourceGroup = resourceGroup;
    }

    @Override
    public Class<?> getObjectType() {
        return AzureContext.class;
    }

    @Override
    protected AzureContext createInstance() throws Exception {
        Azure azure;
        if (subscriptionId == null) {
            azure = credentials.authenticate().withDefaultSubscription();
        } else {
            azure = credentials.authenticate().withSubscription(subscriptionId);
        }
        return new AzureContext(azure, resourceGroup);
    }
}
