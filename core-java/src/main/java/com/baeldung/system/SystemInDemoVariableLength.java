package com.baeldung.system;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class SystemInDemoVariableLength {
    public static void main(String[] args) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String name;

        System.out.print("Enter your name: ");

        try {
            name = reader.readLine();

            System.out.println("Hello " + name);
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
