package com.microsoft.azure.cloud.core.configuration;

import com.microsoft.azure.cloud.core.context.AzureContext;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = AzureContextConfigurationTest.Application.class)
public class AzureContextConfigurationTest {
    @Autowired
    private AzureContext azureContext;

    @Test
    public void azureContextExist() throws Exception {
        assertNotNull(azureContext);
        assertNotNull(azureContext.redisCache());
    }

    @SpringBootApplication
    static class Application {

    }

}