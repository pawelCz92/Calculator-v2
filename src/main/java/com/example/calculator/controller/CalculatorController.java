package com.example.calculator.controller;

import com.example.calculator.service.Calculator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class CalculatorController {

    private final Calculator calculator;

    @PostMapping("/calculate")
    public ResponseEntity<String> calculate(@RequestBody String mathematicalOperation) {
        double result = calculator.calculate(mathematicalOperation);
        ResponseEntity.ok(String.format("The result of a mathematical operation is: %f", result));
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Not implemented yet");
    }
}
