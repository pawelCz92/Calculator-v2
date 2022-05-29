package com.example.calculator.exception;

public class DividingByZeroException extends RuntimeException {
    public DividingByZeroException(String message) {
        super(message);
    }
}
