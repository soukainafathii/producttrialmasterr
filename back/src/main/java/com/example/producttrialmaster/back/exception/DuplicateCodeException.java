package com.example.producttrialmaster.back.exception;

public class DuplicateCodeException extends RuntimeException {
    public DuplicateCodeException(String message) {
        super(message);
    }
}
