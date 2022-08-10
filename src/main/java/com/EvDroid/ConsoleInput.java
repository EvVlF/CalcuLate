package com.EvDroid;

import org.jetbrains.annotations.NotNull;

import java.util.Scanner;

class ConsoleInput {
  private static final Scanner scannerInput = new Scanner(System.in);

  private static @NotNull StringBuilder inputFromUser() {
    StringBuilder input;
    try {
      do {
        System.out.println(
            "Введите выражение.\nРазрешаются операции + - * / без скобок и отрицательных чисел\n");
        input = new StringBuilder(scannerInput.nextLine());
      } while ("".equals(scannerInput.toString()) || " ".equals(scannerInput.toString()));
    } catch (NullPointerException e) {
      throw new NullPointerException("Null значение");
    }
    scannerInput.close();
    return input;
  }

  public static @NotNull StringBuilder getInputFromUser() {
    return inputFromUser();
  }
}
