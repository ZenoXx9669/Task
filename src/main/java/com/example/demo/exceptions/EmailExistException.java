package com.example.demo.exceptions;

import org.springframework.security.core.AuthenticationException;

public class EmailExistException extends AuthenticationException {
    public EmailExistException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public EmailExistException(String msg) {
        super(msg);
    }
}
