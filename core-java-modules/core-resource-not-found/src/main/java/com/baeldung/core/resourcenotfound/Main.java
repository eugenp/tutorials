package com.baeldung.core.resourcenotfound;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        String filePath = "code.csv";
        try (Scanner scanner = new Scanner(getFile(filePath))) {
            int count = 0;
            while (scanner.hasNext()) {
                System.out.println(scanner.nextLine());
                count++;
            }
            System.out.printf("Reading %s lines from %s%n", count, filePath);
        } catch (FileNotFoundException e) {
            System.err.printf("File not found, %s", e.getMessage());
        }
    }

    private static File getFile(String filePath) throws FileNotFoundException {
        URL resource = Main.class.getClassLoader().getResource(filePath);
        if (resource == null) {
            throw new FileNotFoundException();
        }
        return new File(resource.getFile());
    }

    private static File getFileIncorrectly(String filePath) {
        return new File(filePath);
    }
}
