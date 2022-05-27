package com.example.calculator.service;

import com.example.calculator.exception.DividingByZeroException;
import com.example.calculator.exception.InvalidMathOperationException;
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
    @CsvSource({"2+2;4", "3-5;-2", "10-2;8", "10*3;30", "23.5*2.5;58.75", "43/2;21.5", "36/5;7.2"})
        // TODO add some more sources
    void calculate_givenSimpleOperations_returnsCorrectResult(String operation, double expectedResult) {
        //given //when
        double actualResult = calculator.calculate(operation);
        //then
        assertThat(actualResult).isEqualTo(expectedResult);
    }

    @ParameterizedTest
    @CsvSource({"2+2-9;-5", "3-5+9;7", "10-2*3;4", "10*3/2;15", "2+2*2;6", "43/2*0;0", "5*36/5;36", "0/23;0"})
    // TODO add some more sources
    @DisplayName("Returns correct result, according to order of mathematical operations")
    void calculate_givenMoreArgumentsOperations_returnsCorrectResult(String operation, double expectedResult) {
        //given //when
        double actualResult = calculator.calculate(operation);
        //then
        assertThat(actualResult).isEqualTo(expectedResult);
    }

    @ParameterizedTest
    @CsvSource({"(2+2);4", "(2*3);6", "(25.55*3);4", "(3/2);1.5", "(2.564*234.323);600.804172", "(-36+34+2)/4"})
    // TODO add some more sources
    @DisplayName("Returns correct result, for simple operations in brackets")
    void calculate_givenOperationsInBrackets_returnsCorrectResult(String operation, double expectedResult) {
        //given //when
        double actualResult = calculator.calculate(operation);
        //then
        assertThat(actualResult).isEqualTo(expectedResult);
    }

    @ParameterizedTest
    @CsvSource({"6-(2+2);2", "(2*3)/2;3", "(25.55*3)+(23-5);94.65", "(3/2)+1.5;3", "(2.564*234.323)-13;587.804172"})
    // TODO add some more sources
    @DisplayName("Returns correct result, for simple operations in brackets")
    void calculate_givenExtendedOperations_returnsCorrectResult(String operation, double expectedResult) {
        //given //when
        double actualResult = calculator.calculate(operation);
        //then
        assertThat(actualResult).isEqualTo(expectedResult);
    }

    @ParameterizedTest
    @ValueSource(strings = {"6-(2+2)d", "(2*3)/2;3", "(25,55*3)+(23-r5);94.6 5", "(3/2x)+1.53",
            "(2.564*234.3!23)-135v87.804..172", "23+()", "()"})
    // TODO add some more sources
    @DisplayName("Throws InvalidMathOperationException, when given math operation is incorrect")
    void calculate_givenInvalidOperations_throwsException(String operation) {
        //given //when //then
        assertThatThrownBy(() -> calculator.calculate(operation)).isInstanceOf(InvalidMathOperationException.class);
    }

    @ParameterizedTest
    @ValueSource(strings = {"10/0", "(2*3)/0", "((25,55*3)/0)", "(3/2)+16-45/0+4", "0/0-1387.804"})
    // TODO add some more sources
    @DisplayName("Throws InvalidMathOperationException, when dividing by zero occurred in math operation")
    void calculate_dividingByZero_throwsException(String operation) {
        //given //when //then
        assertThatThrownBy(() -> calculator.calculate(operation)).isInstanceOf(DividingByZeroException.class);
    }
}