package com.example.calculator.exception;


public class InvalidMathOperationException extends RuntimeException {
    public InvalidMathOperationException(String message) {
        super(message);
    }
}
