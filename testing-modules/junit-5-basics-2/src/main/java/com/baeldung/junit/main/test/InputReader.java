package com.baeldung.junit.main.test;

import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner;

public class InputReader {

    public String read(InputType inputType, String fileName) {
        switch (inputType) {
        case FILE:
            return readFromFile(fileName);
        case CONSOLE:
            return readFromConsole();
        default:
            return null;
        }
    }

    private String readFromConsole() {
        System.out.println("Enter values for calculation: \n");
        return new Scanner(System.in).nextLine();
    }

    private String readFromFile(String fileName) {
        String readString = null;
        try {
            readString = Files.readString(Path.of(URI.create(fileName)));
            System.out.println(readString);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

}