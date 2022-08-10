package com.EvDroid;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ConsoleInputTest {

  @Test
  @DisplayName("Тест пользовательского ввода")
  void getInputFromUser() {
    String input = "4.2 + 2 * 3 / 3 - 6.1";
    InputStream systemIn = System.in;
    ByteArrayInputStream byteArrayInputStreamIn = new ByteArrayInputStream(input.getBytes());
    System.setIn(byteArrayInputStreamIn);
    Scanner scanner = new Scanner(System.in);
    String result = String.valueOf(ConsoleInput.getInputFromUser());
    assertEquals(input, result);
    System.setIn(systemIn);
    scanner.close();
  }
}
