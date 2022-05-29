package com.example.calculator.service;

import com.example.calculator.exception.DividingByZeroException;
import com.example.calculator.exception.NotSupportedOperationException;
import com.example.calculator.validator.MathOperationValidator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class CalculatorTest {

    private final MathOperationValidator mathOperationValidator = new MathOperationValidator();
    private final Calculator calculator = new Calculator(mathOperationValidator);

    @ParameterizedTest
    @CsvSource(value = {"2-2;0", "3-5;-2.0", "10-2;8.0", "10+2;12.0", "10*3;30.0", "23.5*2.50;58.75",
            "43/2;21.5", "36/5;7.2"}, delimiter = ';')
    void calculate_givenShortAddOrSubtractOperations_returnsCorrectResult(String operation, double expectedResult) {
        //given //when
        Double actualResult = calculator.calculate(operation);
        //then
        assertThat(actualResult).isEqualTo(expectedResult);
    }

    @ParameterizedTest
    @CsvSource(value = {"-10*2;-20.00", "23.5*-2.5;-58.75", "-4/-2;2", "2*2;4.00", "3*5;15.00", "10*3;30.00",
            "43/2;21.5", "36/5;7.2"}, delimiter = ';')
    void calculate_givenShortMultiplyOrDivideOperations_returnsCorrectResult(String operation, double expectedResult) {
        //given //when
        Double actualResult = calculator.calculate(operation);
        //then
        assertThat(actualResult).isEqualTo(expectedResult);
    }

    @ParameterizedTest
    @CsvSource(value = {"-10/2/3;-1.67", "3*5/-9;-1.67", "10*3*-2.5;-75.0", "2*3/9*8*2;10.67", "2*2*2*2*2*2/8/2/2;2.0"},
            delimiter = ';')
    @DisplayName("Returns correct result, according to order of mathematical operations")
    void calculate_givenLongerMultiplyAndDivideOperations_returnsCorrectResult(String operation, double expectedResult) {
        //given //when
        double actualResult = calculator.calculate(operation);
        //then
        assertThat(actualResult).isEqualTo(expectedResult);
    }

    @ParameterizedTest
    @ValueSource(strings = {"(2+2)", "(2*3)", "(25.55*3)", "(3/2)", "(2.564*234.323)+600.804172", "(-36+34+2)/4"})
    @DisplayName("Throw exception, for unsupported operation (brackets).")
    void calculate_givenOperationsInBrackets_throwsNotSupportedOperationException(String operation) {
        //given //when //then
        assertThatThrownBy(() -> calculator.calculate(operation))
                .isInstanceOf(NotSupportedOperationException.class)
                .hasMessage("Operations in brackets not supported.");
    }

    @ParameterizedTest
    @ValueSource(strings = {"6-(2+2)d", "(2*3)/2;3", "(25,55*3)+(23-r5);94.6 5", "(3/2x)+1.53",
            "(2.564*234.3!23)-135v87.804..172", "23+()", "()"})

    @DisplayName("Throws InvalidMathOperationException, when given math operation is incorrect")
    void calculate_givenInvalidOperations_throwsException(String operation) {
        //given //when //then
        assertThatThrownBy(() -> calculator.calculate(operation)).isInstanceOf(NotSupportedOperationException.class);
    }

    @ParameterizedTest
    @ValueSource(strings = {"10/0", "2*3/0", "25.55*3/0+8+13-8", "3/2+16-45/0+4", "0/0-1387.804", "1/0.0000001"})
    @DisplayName("Throws InvalidMathOperationException, when dividing by zero occurred in math operation")
    void calculate_dividingByZero_throwsException(String operation) {
        //given //when //then
        assertThatThrownBy(() -> calculator.calculate(operation)).isInstanceOf(DividingByZeroException.class);
    }
}