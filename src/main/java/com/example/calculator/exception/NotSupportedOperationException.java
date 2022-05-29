package com.example.calculator.exception;


public class NotSupportedOperationException extends RuntimeException {
    public NotSupportedOperationException(String message) {
        super(message);
    }
}
