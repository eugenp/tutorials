package com.baeldung.spotbugs;

import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class Application {

    public static final String NAME = "Name: ";

    private Application() {
    }

    public static String readName() {
        Scanner scanner = new Scanner(System.in, StandardCharsets.UTF_8.displayName());
        String input = scanner.next();
        return NAME.concat(input);
    }
}
