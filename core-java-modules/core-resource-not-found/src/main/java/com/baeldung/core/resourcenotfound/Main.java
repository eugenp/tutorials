package com.baeldung.core.resourcenotfound;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.URL;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        String filePath = "code.csv";
        Scanner scanner = new Scanner(getInputStream(filePath));
        int count = 0;
        while (scanner.hasNext()) {
            System.out.println(scanner.nextLine());
            count++;
        }
        System.out.printf("Reading %s lines from %s%n", count, filePath);
    }

    private static InputStream getInputStream(String filePath) {
        return ClassLoader.getSystemClassLoader().getResourceAsStream(filePath);
    }
}
