package com.jpdev.usuario.infrastructure.exception;

import javax.naming.AuthenticationException;

public class UnauthorizedException extends AuthenticationException {
    public UnauthorizedException(String message) {
        super(message);
    }

}
