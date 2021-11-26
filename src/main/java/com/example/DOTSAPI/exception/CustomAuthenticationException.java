package com.example.DOTSAPI.exception;

import javax.security.sasl.AuthenticationException;

public class CustomAuthenticationException extends AuthenticationException {
    public CustomAuthenticationException(String detail) {
        super(detail);
    }
}
