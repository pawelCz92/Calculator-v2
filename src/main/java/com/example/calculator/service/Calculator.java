package com.example.calculator.service;

import com.example.calculator.enums.MathOperators;
import com.example.calculator.validator.MathOperationValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static com.example.calculator.enums.MathOperators.*;

@Service
@RequiredArgsConstructor
public class Calculator {

    private final static String NUMBERS_REGEX = "-?\\d*\\.?\\d+";
    private final static String OPERATORS_REGEX = String.format("[%s%s%s%s]",
            MULTIPLY.getSymbol(),
            DIVIDE.getSymbol(),
            ADD.getSymbol(),
            SUBTRACT.getSymbol());
    private final static int MAX_ROUND_NUMBER_VALUE = 2;
    private final MathOperationValidator validator;


    public double calculate(@NonNull String mathematicalOperation) {
        validator.validate(mathematicalOperation);
        List<Double> numbers = extractNumbers(mathematicalOperation);
        List<MathOperators> operators = extractOperators(mathematicalOperation);

        reduceMultiplyAndDivide(numbers, operators);
        reduceSumAndSubtract(numbers, operators);
        double result = numbers.get(0);
        return round(result);
    }

    private List<MathOperators> extractOperators(@NonNull String input) {
        List<String> operatorsAsStrings = Arrays.asList(input.split(NUMBERS_REGEX));
        if (operatorsAsStrings.isEmpty()) {
            operatorsAsStrings = new ArrayList<>();
            operatorsAsStrings.add(SUBTRACT.getSymbol());
        }
        return operatorsAsStrings.stream()
                .filter((o) -> !o.isBlank())
                .map(MathOperators::findByOperatorAsString)
                .collect(Collectors.toList());
    }

    private List<Double> extractNumbers(@NonNull String input) {
        List<String> numbersAsStrings = Arrays.asList(input.split(OPERATORS_REGEX));
        List<Double> extractedNumbers = new ArrayList<>();
        for (int i = 0; i < numbersAsStrings.size(); i++) {
            if (numbersAsStrings.get(i).isEmpty()) {
                extractedNumbers.add(Double.parseDouble(numbersAsStrings.get(i + 1)) * -1);
                i++;
                continue;
            }
            extractedNumbers.add(Double.parseDouble(numbersAsStrings.get(i)));
        }
        return extractedNumbers;
    }

    private void reduceMultiplyAndDivide(@NonNull List<Double> numbers, @NonNull List<MathOperators> operators) {
        for (int i = 0; i < operators.size(); i++) {
            if (operators.get(i).equals(ADD) || operators.get(i).equals(SUBTRACT)) {
                continue;
            }
            numbers.set(i, calculateTwoNumbers(numbers.get(i), numbers.get(i + 1), operators.get(i)));
            numbers.remove(i + 1);
            operators.remove(i);
            i--;
        }
    }

    private void reduceSumAndSubtract(@NonNull List<Double> numbers, @NonNull List<MathOperators> operators) {
        for (int i = 0; i < operators.size(); i++) {
            numbers.set(i, calculateTwoNumbers(numbers.get(i), numbers.get(i + 1), operators.get(i)));
            numbers.remove(i + 1);
            operators.remove(i);
            i--;
        }
    }

    private double calculateTwoNumbers(double first, double second, @NonNull MathOperators operators) {
        double result = 0;
        switch (operators) {
            case ADD -> result = first + second;
            case SUBTRACT -> result = first - second;
            case MULTIPLY -> result = first * second;
            case DIVIDE -> result = first / second;
        }
        return result;
    }

    private double round(double number) {
        BigDecimal bigDecimal = BigDecimal.valueOf(number);
        bigDecimal = bigDecimal.setScale(MAX_ROUND_NUMBER_VALUE, RoundingMode.HALF_UP);
        return bigDecimal.doubleValue();
    }
}
