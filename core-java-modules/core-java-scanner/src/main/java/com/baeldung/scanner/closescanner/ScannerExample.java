package com.baeldung.closescanner;
import java.util.Scanner;

public class ScannerExample {
    public String getGreetingMessage(Scanner scanner) {
        System.out.print("Enter your name: ");
        String name = scanner.nextLine();
        return "Hi, " + name + " Welcome to Baeldung";
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        try {
            ScannerExample example = new ScannerExample();
            String message = example.getGreetingMessage(scanner);
            System.out.println(message);
        } finally {
            scanner.close();
        }
    }
}
