package com.jpdev.usuario.infrastructure.exceptions;

public class ResourceNotFondException extends RuntimeException {

    public ResourceNotFondException(String message) {
        super(message);
    }

  public ResourceNotFondException(String message, Throwable throwable) {
    super(message, throwable);
  }
}
