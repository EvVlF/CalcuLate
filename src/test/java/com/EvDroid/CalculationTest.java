package com.EvDroid;

import com.EvDroid.Exceptions.NotCorrectExpression;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CalculationTest {

  @Test
  @DisplayName("Тест вычислений")
  void getCalculation() throws NotCorrectExpression {
    double result = 0.1;
    assertThat(Calculation.getCalculation(sourceForCalcMethod()), is(equalTo(result)));
  }

  @Test
  @DisplayName("Тест на исключение")
  void getCalculationThrow() {
      assertThrows(NotCorrectExpression.class, () -> Calculation.getCalculation(sourceForCalcMethodThrow()));
  }

  @Test
  @DisplayName("ahYouDidItShameOnYou")
  void getDivideByZero() {
    assertThrows(NotCorrectExpression.class, () -> Calculation.getCalculation(sourceForDivideByZeroMethod()));
  }

  static ArrayList<StringBuilder> sourceForCalcMethod() {
    ArrayList<StringBuilder> reversedPolishNotation = new ArrayList<>();
    reversedPolishNotation.add(new StringBuilder("4.2"));
    reversedPolishNotation.add(new StringBuilder("2"));
    reversedPolishNotation.add(new StringBuilder("3"));
    reversedPolishNotation.add(new StringBuilder("*"));
    reversedPolishNotation.add(new StringBuilder("3"));
    reversedPolishNotation.add(new StringBuilder("/"));
    reversedPolishNotation.add(new StringBuilder("+"));
    reversedPolishNotation.add(new StringBuilder("6.1"));
    reversedPolishNotation.add(new StringBuilder("-"));
    return reversedPolishNotation;
  }

  static ArrayList<StringBuilder> sourceForCalcMethodThrow() {
    ArrayList<StringBuilder> reversedPolishNotation = new ArrayList<>();
    reversedPolishNotation.add(new StringBuilder("4.2"));
    reversedPolishNotation.add(new StringBuilder("4.2"));
    return reversedPolishNotation;
  }

  static ArrayList<StringBuilder> sourceForDivideByZeroMethod() {
    ArrayList<StringBuilder> reversedPolishNotation = new ArrayList<>();
    reversedPolishNotation.add(new StringBuilder("4.2"));
    reversedPolishNotation.add(new StringBuilder("0"));
    reversedPolishNotation.add(new StringBuilder("/"));
    return reversedPolishNotation;
  }

}