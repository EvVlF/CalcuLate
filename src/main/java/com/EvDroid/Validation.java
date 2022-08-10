package com.EvDroid;

import com.EvDroid.Exceptions.NotCorrectExpression;
import org.jetbrains.annotations.NotNull;
import java.util.ArrayDeque;

class Validation {

  private static ArrayDeque<StringBuilder> validatedExpression = new ArrayDeque<>();

  private static @NotNull ArrayDeque<StringBuilder> validateEnteredExpression(
      @NotNull StringBuilder enteredExpression) throws NotCorrectExpression {
    char firstChar = enteredExpression.charAt(0);
    char lastChar = enteredExpression.charAt(enteredExpression.length() - 1);
    int index = 0;
    if (!(Character.isDigit(firstChar)) || !(Character.isDigit(lastChar))) {
      throw new NotCorrectExpression("Неверное выражение");
    }
    for (; index < enteredExpression.length(); index++) {
      char currChar = enteredExpression.charAt(index);
      if (Character.isDigit(currChar)) {
        StringBuilder digit =
            new StringBuilder()
                .append(
                    buildDigit(enteredExpression.subSequence(index, enteredExpression.length())));
        validatedExpression.add(digit);
        index += digit.length() - 1;
      } else {
        switch (currChar) {
          case '+':
          case '*':
          case '-':
          case '/':
            validatedExpression.add(new StringBuilder().append(currChar));
            break;
          case ' ':
            continue;
          default:
            throw new RuntimeException("Неверный формат выражения");
        }
      }
    }
    return validatedExpression;
  }

  private static @NotNull StringBuilder buildDigit(@NotNull CharSequence subEnteredExpression)
      throws NotCorrectExpression {
    StringBuilder digit = new StringBuilder();
    int indexOfDigit = 0;
    while (Character.isDigit(subEnteredExpression.charAt(indexOfDigit))
        || '.' == subEnteredExpression.charAt(indexOfDigit)) {
      indexOfDigit++;
      if (indexOfDigit == subEnteredExpression.length()) {
        break;
      }
    }
    digit.append(subEnteredExpression.subSequence(0, indexOfDigit));
    if (!(isDotsInDigitLessThan2(digit)) || '.' == (digit.charAt(digit.length() - 1))) {
      throw new NotCorrectExpression("Неверный формат десятичного числа");
    }
    return digit;
  }

  private static boolean isDotsInDigitLessThan2(
      @NotNull CharSequence digitFromSubEnteredExpression) {
    int count = 0;
    for (int i = 0; i < digitFromSubEnteredExpression.length(); i++) {
      if ('.' == digitFromSubEnteredExpression.charAt(i)) {
        count++;
      }
    }
    return count <= 1;
  }

  public static @NotNull ArrayDeque<StringBuilder> getValidatedExpression(
      StringBuilder enteredExpression) throws NotCorrectExpression {
    return validateEnteredExpression(enteredExpression);
  }
}
