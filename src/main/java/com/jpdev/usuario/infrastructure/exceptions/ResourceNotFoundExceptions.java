package com.jpdev.usuario.infrastructure.exceptions;

public class ResourceNotFoundExceptions extends RuntimeException {

    public ResourceNotFoundExceptions(String message) {
        super(message);
    }

    public ResourceNotFoundExceptions(String message, Throwable throwable) {
        super(message, throwable);
    }
}
