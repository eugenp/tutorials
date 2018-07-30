package com.baeldung.fileparser;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class BufferedReaderExample {

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

        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {

            while (br.ready()) {
                result.add(br.readLine());
            }
            return result;
        }

    }

}
