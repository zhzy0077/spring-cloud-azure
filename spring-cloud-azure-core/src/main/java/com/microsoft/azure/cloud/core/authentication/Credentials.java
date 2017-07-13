package com.microsoft.azure.cloud.core.authentication;

import com.microsoft.azure.management.Azure;

import java.io.IOException;

public interface Credentials {
    Azure.Authenticated authenticate() throws IOException;
}
