package com.example.calculator.enums;

import lombok.Getter;

@Getter
public enum MathOperators {
    MULTIPLY("*", 1),
    DIVIDE("/", 1),
    ADD("+", 2),
    SUBTRACT("-", 2);

    private final String symbol;
    private final int orderOfMathOperations;

    MathOperators(String symbol, int orderOfMathOperations) {
        this.symbol = symbol;
        this.orderOfMathOperations = orderOfMathOperations;
    }

    public static MathOperators findByOperatorAsString(String operator) {
        for (MathOperators value : MathOperators.values()) {
            if (value.symbol.equals(operator)) {
                return value;
            }
        }
        throw new IllegalArgumentException("Operator: \"" + operator + "\" not found!");
    }
}