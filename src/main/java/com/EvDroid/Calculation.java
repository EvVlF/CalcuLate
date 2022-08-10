package com.EvDroid;

import com.EvDroid.Exceptions.NotCorrectExpression;
import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;
import java.util.ArrayDeque;
import java.util.ArrayList;

import static com.EvDroid.ConsoleInput.getInputFromUser;
import static com.EvDroid.Parser.getParsedInReversePolishNotation;
import static com.EvDroid.Validation.getValidatedExpression;

class Calculation {

  public static void main(String[] args) throws NotCorrectExpression {
    StringBuilder enteredExpression = getInputFromUser();
    ArrayDeque<StringBuilder> validatedExpression = getValidatedExpression(enteredExpression);
    ArrayList<StringBuilder> reversedInPolishNotationExpression =
        getParsedInReversePolishNotation(validatedExpression);
    System.out.println("Результат: " + getCalculation(reversedInPolishNotationExpression));
  }

  private static double calculation(@NotNull ArrayList<StringBuilder> reversedPolishNotation)
      throws NotCorrectExpression {
    for (int i = 0; reversedPolishNotation.size() != 1; i++) {
      if (reversedPolishNotation.size() == 2 || reversedPolishNotation.size() == 0) {
        throw new NotCorrectExpression("Неверное выражение");
      }
      if (Character.isDigit(reversedPolishNotation.get(i).charAt(0))) {
        continue;
      } else {
        StringBuilder result = new StringBuilder();
        switch (reversedPolishNotation.get(i).charAt(0)) {
          case '+':
            result = add(reversedPolishNotation.get(i - 2), reversedPolishNotation.get(i - 1));
            reversedPolishNotation.set(i, result);
            reversedPolishNotation.removeAll(reversedPolishNotation.subList(i - 2, i));
            i = 0;
            break;
          case '-':
            result = subtract(reversedPolishNotation.get(i - 2), reversedPolishNotation.get(i - 1));
            reversedPolishNotation.set(i, result);
            reversedPolishNotation.removeAll(reversedPolishNotation.subList(i - 2, i));
            i = 0;
            break;
          case '*':
            result = multiply(reversedPolishNotation.get(i - 2), reversedPolishNotation.get(i - 1));
            reversedPolishNotation.set(i, result);
            reversedPolishNotation.removeAll(reversedPolishNotation.subList(i - 2, i));
            i = 0;
            break;
          case '/':
            result = divide(reversedPolishNotation.get(i - 2), reversedPolishNotation.get(i - 1));
            reversedPolishNotation.set(i, result);
            reversedPolishNotation.removeAll(reversedPolishNotation.subList(i - 2, i));
            i = 0;
            break;
        }
      }
    }
    return Double.parseDouble(reversedPolishNotation.get(0).toString());
  }

  private static @NotNull StringBuilder add(
      @NotNull StringBuilder operand1, @NotNull StringBuilder operand2) {
    return new StringBuilder()
        .append(new BigDecimal(operand1.toString()).add(new BigDecimal(operand2.toString())));
  }

  private static @NotNull StringBuilder subtract(
      @NotNull StringBuilder operand1, @NotNull StringBuilder operand2) {
    return new StringBuilder()
        .append(new BigDecimal(operand1.toString()).subtract(new BigDecimal(operand2.toString())));
  }

  private static @NotNull StringBuilder multiply(
      @NotNull StringBuilder operand1, @NotNull StringBuilder operand2) {
    return new StringBuilder()
        .append(new BigDecimal(operand1.toString()).multiply(new BigDecimal(operand2.toString())));
  }

  @SuppressWarnings("BigDecimalMethodWithoutRoundingCalled")
  private static @NotNull StringBuilder divide(
      @NotNull StringBuilder operand1, @NotNull StringBuilder operand2)
      throws NotCorrectExpression {
    if (operand2.toString().equals("0")) {
      throw new NotCorrectExpression("Деление на ноль");
    }
    return new StringBuilder()
        .append(new BigDecimal(operand1.toString()).divide(new BigDecimal(operand2.toString())));
  }

  public static double getCalculation(ArrayList<StringBuilder> reversedPolishNotation)
      throws NotCorrectExpression {
    return calculation(reversedPolishNotation);
  }
}
