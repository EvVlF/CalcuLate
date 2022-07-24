package com.EvDroid;

import com.EvDroid.Exceptions.NotCorrectExpression;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayDeque;

class Validation {

  private static final StringBuilder enteredExpression = ConsoleHandler.inputFromUser();
  private static final ArrayDeque<StringBuilder> validatedExpression = new ArrayDeque<>();

  private static ArrayDeque<StringBuilder> validateEnteredExpression() throws NotCorrectExpression {
    if (!((Character.isDigit(enteredExpression.charAt(0)))
        && (Character.isDigit(enteredExpression.charAt(enteredExpression.length() - 1))))) {
      throw new NotCorrectExpression("Неверное выражение");
    }
    for (int i = 0; i < enteredExpression.length(); i++) {
      if (' ' == enteredExpression.charAt(i)) {
        continue;
      } else if (Character.isDigit(enteredExpression.charAt(i))) {
        StringBuilder numberBuilderFromInput = new StringBuilder();
        while ((Character.isDigit(enteredExpression.charAt(i))
            || '.' == enteredExpression.charAt(i))) {
          if (('.' == enteredExpression.charAt(i))
              && (enteredExpression.charAt(i - 1) == enteredExpression.charAt(i))) {
            throw new NotCorrectExpression("Неверный формат десятичного числа");
          }
          numberBuilderFromInput.append(enteredExpression.charAt(i));
          i++;
          if (i >= enteredExpression.length()) {
            break;
          }
        }
        i--;
        if (isDotsInNumberLessThan2(numberBuilderFromInput)) {
          validatedExpression.add(numberBuilderFromInput);
        } else {
          throw new NotCorrectExpression("Неверный формат десятичного числа");
        }
      } else {
        switch (enteredExpression.charAt(i)) {
          case '+':
          case '*':
          case '-':
          case '/':
            validatedExpression.add(new StringBuilder().append(enteredExpression.charAt(i)));
            break;
          default:
            throw new RuntimeException("Неверный формат выражения");
        }
      }
    }
    return validatedExpression;
  }

  private static boolean isDotsInNumberLessThan2(@NotNull StringBuilder digitBuilderFromInput) {
    int count = 0;
    for (int i = 0; i < digitBuilderFromInput.length(); i++) {
      if ('.' == digitBuilderFromInput.charAt(i)) {
        count++;
      }
    }
    return count <= 1;
  }

  public static ArrayDeque<StringBuilder> getValidatedExpression() throws NotCorrectExpression {
    return validateEnteredExpression();
  }
}
