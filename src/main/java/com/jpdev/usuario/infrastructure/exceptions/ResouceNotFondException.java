package com.jpdev.usuario.infrastructure.exceptions;

public class ResouceNotFondException extends RuntimeException{

    public ResouceNotFondException(String msg){
        super(msg);
    }

    public ResouceNotFondException(String msg, Throwable throwable){
        super(msg, throwable);
    }
}
