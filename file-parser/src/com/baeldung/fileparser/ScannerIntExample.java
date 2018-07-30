package com.baeldung.fileparser;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class ScannerIntExample {

    private static final String FILENAME = "src/resources/num.txt";

    public static void main(String[] args) {
        try {
            System.out.println(generateArrayListFromFile(FILENAME));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static ArrayList<Integer> generateArrayListFromFile(String filename) throws IOException {
        
        ArrayList<Integer> result = new ArrayList<>();

        try (Scanner s = new Scanner(new FileReader(filename))) {

            while (s.hasNext()) {
                result.add(s.nextInt());
            }
            return result;
        }

    }

}
