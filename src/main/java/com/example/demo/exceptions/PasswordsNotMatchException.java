package com.example.demo.exceptions;

import org.springframework.security.core.AuthenticationException;

public class PasswordsNotMatchException extends AuthenticationException {
    public PasswordsNotMatchException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public PasswordsNotMatchException(String msg) {
        super(msg);
    }
}
