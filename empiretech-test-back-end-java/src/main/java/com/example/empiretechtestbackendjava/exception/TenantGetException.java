package com.example.empiretechtestbackendjava.exception;

import org.springframework.web.client.RestClientException;

public class TenantGetException extends RuntimeException {
    public TenantGetException(String message, RestClientException exception) {
        super(message, exception);
    }
}
