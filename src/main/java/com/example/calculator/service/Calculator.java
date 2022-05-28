package com.example.calculator.service;

import com.example.calculator.enums.OperatorSymbols;
import com.example.calculator.model.NumbersPair;
import com.example.calculator.validator.MathOperationValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Stream;

import static com.example.calculator.enums.OperatorSymbols.*;

@Service
@RequiredArgsConstructor
public class Calculator {

    private final static String NUMBERS_REGEX = "\\d*\\.?\\d+";
    private final static String OPERATORS_REGEX = String.format("[%s%s%s%s]", MULTIPLY, DIVIDE, ADD, SUBTRACT);
    private final MathOperationValidator validator;


    public double calculate(String mathematicalOperation) {
        validator.validate(mathematicalOperation);
        List<Double> numbers = extractNumbers(mathematicalOperation);
        List<OperatorSymbols> operators = extractOperators(mathematicalOperation);

        throw new IllegalStateException("not implemented yet");
    }

    private List<OperatorSymbols> extractOperators(String input) {
        return Arrays.stream((input.split(OPERATORS_REGEX)))
                .map(OperatorSymbols::valueOf)
                .toList();
    }

    private List<Double> extractNumbers(String input) {
        return Stream.of(input.split(NUMBERS_REGEX))
                .map(Double::parseDouble)
                .toList();
    }

    private List<Double> calculateMultiplyAndDivideOperations(List<Double> allNumbers, List<OperatorSymbols> allOperators) {
        LinkedList<Double> numbers = new LinkedList<>(allNumbers);
        List<OperatorSymbols> operators = new ArrayList<>(allOperators);
        List<NumbersPair> numbersPairs = new ArrayList<>();

        NumbersPair pair;
        if (numbers.size() % 2 == 0) {
            numbers.addFirst(0d);
        }
        for (int i = 0; i < operators.size(); i++) {
            if (operators.get(i).equals(ADD) || operators.get(i).equals(SUBTRACT)) {
                continue;
            }
            numbersPairs.add(NumbersPair.builder()
                    .firstNumber(numbers.get(i))
                    .operatorBetween(operators.get(i))
                    .secondNumber(numbers.get(i + 1))
                    .build());

        }
    }

    private NumbersPair calculateAndReturnAsFirstNumber(NumbersPair numbersPair) {
        Objects.requireNonNull(numbersPair, "NumberPair must not be null for calculating and reducing.");
        OperatorSymbols operator = numbersPair.getOperatorBetween();
        double first = numbersPair.getFirstNumber();
        double second = numbersPair.getSecondNumber();
        double result = 0;

        switch (operator) {
            case ADD -> result = first + second;
            case SUBTRACT -> result = first - second;
            case MULTIPLY -> result = first * second;
            case DIVIDE -> result = first / second;
        }
        return NumbersPair.builder()
                .firstNumber(result)
                .build();
    }
}

    /*
    private List<Double> calculateMultiplyAndDivideOperations(List<Double> numbers, List<OperatorSymbols> operators) {
        List<Double> copyOfNumbers = new ArrayList<>(numbers);
        OperatorSymbols operator;

        for (int i = 0; i < operators.size(); i++) {
            operator = operators.get(i);
            if (operator.equals(ADD) || operator.equals(SUBTRACT)) {
                continue;
            }
            copyOfNumbers.set(i, calculateTwoNumbers(copyOfNumbers.get(i), copyOfNumbers.get(i + 1), operators.get(i)));
            copyOfNumbers.remove(i + 1);
            operators.remove(i);
            i--;
        }
        return copyOfNumbers;
    }

    private void calculateSumAndSubtractOperations(List<Double> numbers, List<OperatorSymbols> operators) {
        List<>
        for (int i = 0; i < operators.size(); i++) {
            numbers.set(i, calculateTwoNumbers(numbers.get(i), numbers.get(i + 1), operators.get(i)));
            numbers.remove(i + 1);
            operators.remove(i);
            i--;
        }
    }

    private Double calculateTwoNumbers(double a, double b, OperatorSymbols operator) {
        switch (operator) {
            case ADD -> {
                return a + b;
            }
            case SUBTRACT -> {
                return a - b;
            }
            case MULTIPLY -> {
                return a * b;
            }
            case DIVIDE -> {
                if (b != 0) {
                    return a / b;
                }
                throw new IllegalArgumentException("Divide by 0 can't be done");
            }
            default -> throw new IllegalArgumentException("There is no such operator: " + operator);
        }
    }
*/
}
