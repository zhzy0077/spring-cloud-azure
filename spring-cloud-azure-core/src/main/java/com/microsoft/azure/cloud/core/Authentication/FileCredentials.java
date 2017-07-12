package com.microsoft.azure.cloud.core.authentication;

import com.microsoft.azure.management.Azure;
import org.springframework.util.Assert;

import java.io.File;
import java.io.IOException;

public class FileCredentials implements Credentials {
    private final File credentialFile;

    public FileCredentials(File credentialFile) {
        Assert.notNull(credentialFile, "credentialFile must not be null");
        this.credentialFile = credentialFile;
    }

    public FileCredentials(String credentialFile) {
        Assert.hasLength(credentialFile, "credentialFile must not be null or empty");
        this.credentialFile = new File(credentialFile);
    }

    @Override
    public Azure.Authenticated authenticate() throws IOException {
        return Azure.authenticate(credentialFile);
    }
}
