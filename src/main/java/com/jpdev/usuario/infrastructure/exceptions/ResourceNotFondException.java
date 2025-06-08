package com.jpdev.usuario.infrastructure.exceptions;

public class ResourceNotFondException extends RuntimeException{

    public ResourceNotFondException(String msg){
        super(msg);
    }

    public ResourceNotFondException(String msg, Throwable throwable){
        super(msg, throwable);
    }
}
