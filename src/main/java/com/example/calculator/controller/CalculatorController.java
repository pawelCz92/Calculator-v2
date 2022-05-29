package com.example.calculator.controller;

import com.example.calculator.exception.DividingByZeroException;
import com.example.calculator.exception.NotSupportedOperationException;
import com.example.calculator.service.Calculator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class CalculatorController {

    private final Calculator calculator;

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/calculate")
    public String calculate(@RequestBody String mathematicalOperation) {
        double result = calculator.calculate(mathematicalOperation);
        return String.format("The result of a mathematical operation is: %.2f", result);
    }

    @ExceptionHandler(NotSupportedOperationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected String handle(NotSupportedOperationException exception) {
        return exception.getMessage();
    }

    @ExceptionHandler(DividingByZeroException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected String handle(DividingByZeroException exception) {
        return exception.getMessage();
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String handle(Exception exception) {
        return "Uncaught server exception.";
    }
}
