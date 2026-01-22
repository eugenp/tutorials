package com.baeldung.scannerinput;

import java.util.Scanner;

public class SampleScannerSentinel {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        try {
            while (scan.hasNextLine()) {
                String line = scan.nextLine().toLowerCase();
                if (line.equals("exit")) {
                    System.out.println("Exiting program...");
                    break;
                }
                System.out.println(line);
            }
        } finally {
            scan.close();
        }
    }
}
