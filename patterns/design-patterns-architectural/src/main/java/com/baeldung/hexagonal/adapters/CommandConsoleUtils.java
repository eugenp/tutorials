package com.baeldung.hexagonal.adapters;

import java.util.Scanner;

public class CommandConsoleUtils {

    public static String readString(Scanner scanner) {
        System.out.print("> ");
        return scanner.next();
    }

}
