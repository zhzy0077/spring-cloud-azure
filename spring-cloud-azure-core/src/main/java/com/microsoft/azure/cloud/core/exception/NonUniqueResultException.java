package com.microsoft.azure.cloud.core.exception;

/**
 * Created by t-zhzhe on 7/11/2017 in spring-cloud-azure.
 */
public class NonUniqueResultException extends RuntimeException {

    public NonUniqueResultException(String resourceType) {
        super("The " + resourceType + " is not unique in such resource group.");
    }
}
