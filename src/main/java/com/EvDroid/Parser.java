package com.EvDroid;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Unmodifiable;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Objects;

class Parser {
  private static final ArrayDeque<StringBuilder> asStack = new ArrayDeque<>();
  private static final ArrayDeque<StringBuilder> preReversedPolishNotation = new ArrayDeque<>();

  @SuppressWarnings("SameReturnValue")
  private static ArrayDeque<StringBuilder> parseInReversePolishNotation(
      @NotNull ArrayDeque<StringBuilder> validatedExpression) {
    while (!validatedExpression.isEmpty()) {
      StringBuilder elementOfValidatedExpression = validatedExpression.pop();
      char charAt0 = elementOfValidatedExpression.charAt(0);
      if (Character.isDigit(charAt0)) {
        preReversedPolishNotation.add(elementOfValidatedExpression);
      } else {
        switch (charAt0) {
          case '+':
          case '-':
            while (!asStack.isEmpty()
                && ('+' == (Objects.requireNonNull(asStack.peek())).charAt(0)
                    || '-' == (Objects.requireNonNull(asStack.peek())).charAt(0)
                    || '*' == (Objects.requireNonNull(asStack.peek())).charAt(0)
                    || '/' == (Objects.requireNonNull(asStack.peek())).charAt(0))) {
              preReversedPolishNotation.add(asStack.pop());
            }
            asStack.push(elementOfValidatedExpression);
            break;
          case '*':
          case '/':
            while (!asStack.isEmpty()
                && ('*' == (Objects.requireNonNull(asStack.peek())).charAt(0)
                    || '/' == (Objects.requireNonNull(asStack.peek())).charAt(0))) {
              preReversedPolishNotation.add(asStack.pop());
            }
            asStack.push(elementOfValidatedExpression);
            break;
        }
      }
    }
    while (!asStack.isEmpty()) {
      preReversedPolishNotation.add(asStack.pop());
    }
    return preReversedPolishNotation;
  }

  public static @NotNull @Unmodifiable ArrayList<StringBuilder> getParsedInReversePolishNotation(
      ArrayDeque<StringBuilder> validatedExpression) {
    return new ArrayList<>(parseInReversePolishNotation(validatedExpression));
  }
}
