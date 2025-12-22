package com.baeldung.scannerinput;

import java.util.Scanner;

public class SampleScannerScan {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        try {
            while (scan.hasNextLine()) {
                String line = scan.nextLine();
                if (line == null) {
                    System.out.println("Exiting program (null check)...");
                    System.exit(0);
                }
                System.out.println("Input was: " + line);
            }
        } finally {
            scan.close();
        }
    }
}
