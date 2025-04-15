package com.jpdev.usuario.infrastructure.exceptions;

public class ConflicException extends RuntimeException{

    public ConflicException(String msg){
        super(msg);
    }

    public ConflicException(String msg, Throwable throwable){
        super(msg, throwable);
    }
}
