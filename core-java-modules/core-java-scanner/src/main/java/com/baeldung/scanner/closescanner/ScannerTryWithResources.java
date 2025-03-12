package com.baeldung.scanner.closescanner;
import java.util.Scanner;

public class ScannerTryWithResources {

    public String getGreetingMessage(Scanner scanner) {
        System.out.print("Enter your name: ");
        String name = scanner.nextLine();
        return "Hi, " + name + " Welcome to Baeldung";
    }

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            ScannerTryWithResources example = new ScannerTryWithResources();
            String message = example.getGreetingMessage(scanner);
            System.out.println(message);
        }
    }
}
