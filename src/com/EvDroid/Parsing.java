package com.EvDroid;

import com.EvDroid.Exceptions.NotCorrectExpression;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Unmodifiable;

import java.util.ArrayDeque;
import java.util.Objects;

class Parsing {
  private static final ArrayDeque<StringBuilder> validatedExpression;

  static {
    try {
      validatedExpression = Validation.getValidatedExpression();
    } catch (NotCorrectExpression e) {
      throw new RuntimeException(e);
    }
  }

  private static final ArrayDeque<StringBuilder> asStack = new ArrayDeque<>();
  private static final ArrayDeque<StringBuilder> preReversedPolishNotation = new ArrayDeque<>();

  @SuppressWarnings("SameReturnValue")
  private static ArrayDeque<StringBuilder> preParseInPolishNotation() {
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
                && ('+' == asStack.peek().charAt(0)
                    || '+' == Objects.requireNonNull(asStack.peek()).charAt(0)
                    || '*' == Objects.requireNonNull(asStack.peek()).charAt(0)
                    || '/' == (Objects.requireNonNull(asStack.peek())).charAt(0))) {
              preReversedPolishNotation.add(asStack.pop());
            }
            asStack.push(elementOfValidatedExpression);
            break;
          case '*':
          case '/':
            while (!asStack.isEmpty()
                && ('*' == asStack.peek().charAt(0)
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

  public static @NotNull @Unmodifiable ArrayDeque<StringBuilder> getParsedInPolishNotation() {
    return preParseInPolishNotation();
  }
}
