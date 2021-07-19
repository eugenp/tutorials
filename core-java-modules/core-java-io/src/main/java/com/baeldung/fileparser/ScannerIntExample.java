package com.baeldung.fileparser;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class ScannerIntExample {

    protected static ArrayList<Integer> generateArrayListFromFile(String filename) throws IOException {
        
        ArrayList<Integer> result = new ArrayList<>();

        try (Scanner s = new Scanner(new FileReader(filename))) {

            while (s.hasNext()) {
                result.add(s.nextInt());
            }
            return result;
        }

    }

}
