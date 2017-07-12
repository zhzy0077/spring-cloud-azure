package com.microsoft.azure.cloud.core.authentication;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.File;

public class FileCredentialsTest {

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testAuthenticateWithRightCredential() throws Exception {
        Credentials credentials = new FileCredentials(new File(this.getClass().getResource("/my.azureauth").toURI()));
        Assert.assertNotNull(credentials.authenticate().subscriptions().list());
    }

}