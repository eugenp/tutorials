package com.baeldung.java9.delimiters;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;

public class DelimiterDemo {

    public static List<String> scannerWithDelimiter(String input, String delimiter) {
        try (Scanner scan = new Scanner(input)) {
            scan.useDelimiter(delimiter);
            List<String> result = new ArrayList<String>();
            scan.forEachRemaining(result::add);
            return result;
        }
    }

    public static List<String> scannerWithDelimiterUsingPattern(String input, Pattern delimiter) {
        try (Scanner scan = new Scanner(input)) {
            scan.useDelimiter(delimiter);
            List<String> result = new ArrayList<String>();
            scan.forEachRemaining(result::add);
            return result;
        }
    }

    public static List<String> baseScanner(String input) {
        try (Scanner scan = new Scanner(input)) {
            List<String> result = new ArrayList<String>();
            scan.forEachRemaining(result::add);
            return result;
        }
    }

}
