package com.EvDroid;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.ArrayDeque;
import static org.junit.jupiter.api.Assertions.assertEquals;

class ParserTest {

  @Test
  @DisplayName("true_ifGetParsedInReversePolishNotation {0}")
  void true_ifGetParsedInPolishNotation() {
    ArrayDeque<StringBuilder> expectedEnteredExpression = new ArrayDeque<>();
    expectedEnteredExpression.add(new StringBuilder("4.2"));
    expectedEnteredExpression.add(new StringBuilder("2"));
    expectedEnteredExpression.add(new StringBuilder("3"));
    expectedEnteredExpression.add(new StringBuilder("*"));
    expectedEnteredExpression.add(new StringBuilder("3"));
    expectedEnteredExpression.add(new StringBuilder("/"));
    expectedEnteredExpression.add(new StringBuilder("+"));
    expectedEnteredExpression.add(new StringBuilder("6.1"));
    expectedEnteredExpression.add(new StringBuilder("-"));
    assertEquals(
        expectedEnteredExpression.toString(),
        (Parser.getParsedInReversePolishNotation(sourceForRPNMethod()).toString()));
  }

  static ArrayDeque<StringBuilder> sourceForRPNMethod() {
    ArrayDeque<StringBuilder> expectedEnteredExpression = new ArrayDeque<>();
    expectedEnteredExpression.add(new StringBuilder("4.2"));
    expectedEnteredExpression.add(new StringBuilder("+"));
    expectedEnteredExpression.add(new StringBuilder("2"));
    expectedEnteredExpression.add(new StringBuilder("*"));
    expectedEnteredExpression.add(new StringBuilder("3"));
    expectedEnteredExpression.add(new StringBuilder("/"));
    expectedEnteredExpression.add(new StringBuilder("3"));
    expectedEnteredExpression.add(new StringBuilder("-"));
    expectedEnteredExpression.add(new StringBuilder("6.1"));
    return expectedEnteredExpression;
  }
}
