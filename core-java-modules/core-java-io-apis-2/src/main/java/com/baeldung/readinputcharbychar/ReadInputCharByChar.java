package com.baeldung.readinputcharbychar;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

public class ReadInputCharByChar {

    public static void main(String[] args) throws IOException {
        readInputFromConsoleUsingBufferStream();

        String filePath = "example.txt";
        readInputFileUsingFileReader(filePath);

        Scanner scanner = new Scanner(System.in);
        readInputFromConsoleUsingScanner(scanner);
    }

    public static void readInputFromConsoleUsingBufferStream() throws IOException {
        BufferedReader buffer = new BufferedReader(
                new InputStreamReader(System.in));
        int c = 0;
        while ((c = buffer.read()) != -1) {
            char character = (char) c;
            System.out.println(character);
        }
    }

    public static void readInputFileUsingFileReader(String filePath) throws IOException {
        try (FileReader fileReader = new FileReader(filePath)) {
            int charCode;
            while ((charCode = fileReader.read()) != -1) {
                char character = (char) charCode;
                System.out.println(character);
            }
        }
    }

    public static void readInputFromConsoleUsingScanner(Scanner scanner) {
        while (scanner.hasNext()) {
            String token = scanner.next();
            char[] characters = token.toCharArray();
            for (char character : characters) {
                System.out.print(character);
            }
        }
    }
}