package com.baeldung.systemin;

import java.util.Scanner;

public class Application {

    public static final String NAME = "Name: ";

    private Application() {
    }

    public static String readName() {
        Scanner scanner = new Scanner(System.in);
        String input = scanner.next();
        return NAME.concat(input);
    }
}
