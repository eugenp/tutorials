package com.baeldung.fileparser;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class FileReaderExample {

    private static final String FILENAME = "src/resources/txt.txt";

    public static void main(String[] args) {
        try {
            System.out.println(generateArrayListFromFile(FILENAME));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static ArrayList<Character> generateArrayListFromFile(String filename) throws IOException {

        ArrayList<Character> result = new ArrayList<>();

        try (FileReader f = new FileReader(filename)) {

            while (f.ready()) {
                char c = (char) f.read();

                if (c != ' ' && c != '\t' && c != '\n') {
                    result.add(c);
                }
            }
            return result;
        }

    }

}
