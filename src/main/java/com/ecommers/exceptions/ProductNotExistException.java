package com.ecommers.exceptions;

public class ProductNotExistException extends IllegalAccessException{
    public ProductNotExistException(String msg) {
        super(msg);
    }
}
