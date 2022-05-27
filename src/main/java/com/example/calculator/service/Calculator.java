package com.example.calculator.service;

import com.example.calculator.validator.MathOperationValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class Calculator {

    private final MathOperationValidator validator;

    public double calculate(String mathematicalOperation) {
        validator.validate(mathematicalOperation);
        return 0;
    }


}
