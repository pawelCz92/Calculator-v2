package com.example.calculator.enums;

import lombok.Getter;

@Getter
public enum OperatorSymbols {
    ADD("+"),
    SUBTRACT("-"),
    MULTIPLY("/"),
    DIVIDE("*");

    private final String symbol;

    OperatorSymbols(String symbol) {
        this.symbol = symbol;
    }
}
