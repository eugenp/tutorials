package com.baeldung.hexagonal;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("Dear User \n Select 1 for writing to console");
        System.out.println("Select 2 for writing to file");

        int selection = sc.nextInt();

        System.out.println("Enter text to write to console or file");
        String text = sc.next();

        if (selection == 1) {
            ConsoleWriter consoleWriter = new ConsoleWriter();
            consoleWriter.write(text);
        }

        if (selection == 2) {
            FileWriter fileWriter = new FileWriter();
            fileWriter.write(text);
        }
    }
}
