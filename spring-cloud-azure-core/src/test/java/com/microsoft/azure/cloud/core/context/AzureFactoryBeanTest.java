package com.microsoft.azure.cloud.core.context;

import com.microsoft.azure.cloud.core.authentication.Credentials;
import com.microsoft.azure.cloud.core.authentication.FileCredentials;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.File;
import java.net.URISyntaxException;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { AzureFactoryBeanTest.Config.class })
public class AzureFactoryBeanTest {
    @Configuration
    static class Config {
        @Bean
        public Credentials credentials() {
            try {
                return new FileCredentials(new File(this.getClass().getResource("/my.azureauth").toURI()));
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
            return null;
        }
        @Bean
        public AzureContextFactoryBean azureFactoryBean(Credentials credentials) {
            return new AzureContextFactoryBean(credentials);
        }
    }

    @Autowired
    private AzureContext azureContext;


    @Test
    public void azureInject() throws Exception {
        Assert.assertNotNull(azureContext);
    }
}