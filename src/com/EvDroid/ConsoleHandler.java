package com.EvDroid;

import org.jetbrains.annotations.NotNull;

import java.util.Scanner;

class ConsoleHandler {
    private static final Scanner scannerInput = new Scanner(System.in);

    public static @NotNull StringBuilder inputFromUser() {
        StringBuilder input;
        do {
            System.out.println("Введите выражение:\n");
            input = new StringBuilder(scannerInput.nextLine());
        } while ("".equals(scannerInput.toString()) || " ".equals(scannerInput.toString()));
        scannerInput.close();
        return input;
    }
}
