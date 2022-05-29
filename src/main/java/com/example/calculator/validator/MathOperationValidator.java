package com.example.calculator.validator;

import com.example.calculator.exception.DividingByZeroException;
import com.example.calculator.exception.NotSupportedOperationException;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.util.regex.Pattern;

@Service
public class MathOperationValidator {

    private static final Pattern VALIDATION_PATTERN =
            Pattern.compile("-?\\(?-?\\d+(\\.\\d+)?([*/+-]-?\\d+(\\.\\d+)?)+\\)?([*/+-]-?\\d+(\\.\\d+)?)?");
    private static final Pattern DIVIDE_BY_ZERO_PATTERN = Pattern.compile(".*\\d*/0\\d*.*");
    private static final Pattern BRACKETS_PATTERN = Pattern.compile(".*[()].*");

    public void validate(@NonNull String input) {
        if (!VALIDATION_PATTERN.matcher(input).matches()) {
            throw new NotSupportedOperationException("Mathematical operation: \"" + input + "\" not supported.");
        }
        if (DIVIDE_BY_ZERO_PATTERN.matcher(input).matches()) {
            throw new DividingByZeroException("Dividing by 0 cannot be done");
        }
        if (BRACKETS_PATTERN.matcher(input).matches()) {
            throw new NotSupportedOperationException("Operations in brackets not supported.");
        }
    }
}
