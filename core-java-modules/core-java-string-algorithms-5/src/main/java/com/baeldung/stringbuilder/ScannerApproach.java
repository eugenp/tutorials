package com.example;

import java.util.Scanner;
import java.io.StringReader;

public class ScannerApproach {

    public static void main(String[] args) {

        StringBuilder sb = new StringBuilder(
            "StringBuilder\nScanner Approach\r\nLine by Line Reading\rAnother line"
        );

        Scanner scanner = new Scanner(new StringReader(sb.toString()));

        while (scanner.hasNextLine()) {
            System.out.println(scanner.nextLine());
        }

        scanner.close();
    }

}
