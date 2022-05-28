package com.example.calculator.model;

import com.example.calculator.enums.OperatorSymbols;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class NumbersPair {

  //  private OperatorSymbols operatorBeforePreviousNumber;
  @Builder.Default
  private double firstNumber = 0;
  private OperatorSymbols operatorBetween;
  private double secondNumber;
  private OperatorSymbols operatorAfter;

}
