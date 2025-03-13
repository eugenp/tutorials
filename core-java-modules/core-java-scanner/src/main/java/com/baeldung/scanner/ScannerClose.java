package com.baeldung.scanner;
import java.util.Scanner;

public class ScannerClose {
    public String getGreetingMessage(Scanner scanner) {
        System.out.print("Enter your name: ");
        String name = scanner.nextLine();
        return "Hi, " + name + " Welcome to Baeldung";
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        try {
            ScannerClose example = new ScannerClose();
            String message = example.getGreetingMessage(scanner);
            System.out.println(message);
        } finally {
            scanner.close();
        }
    }
}
