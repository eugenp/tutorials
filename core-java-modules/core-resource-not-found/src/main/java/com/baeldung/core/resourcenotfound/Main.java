package com.baeldung.core.resourcenotfound;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        String path = "code.csv";
        ResourceFileReader reader = new ResourceFileReader(path);

        Scanner in = new Scanner(System.in);
        System.out.print("$ ");
        String input = in.nextLine();
        while (!input.equalsIgnoreCase("exit")) {
            if (reader.isCodeValid(input)) {
                System.out.println("Valid");
            } else {
                System.out.println("Not Valid");
            }
            System.out.print("$ ");
            input = in.nextLine();
        }
    }
}
