package com.baeldung.fileparser;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class ScannerStringExample {

    private static final String FILENAME = "src/resources/txt.txt";

    public static void main(String[] args) {
        try {
            System.out.println(generateArrayListFromFile(FILENAME));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static ArrayList<String> generateArrayListFromFile(String filename) throws IOException {
        
        ArrayList<String> result = new ArrayList<>();

        try (Scanner s = new Scanner(new FileReader(filename))) {

            while (s.hasNext()) {
                result.add(s.nextLine());
            }
            return result;
        }

    }

}
