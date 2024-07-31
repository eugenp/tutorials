package com.baeldung.systemin;

import java.io.IOException;

class SystemInRead {
    static void readSingleCharacter() {
        System.out.println("Enter a character:");
        try {
            int input = System.in.read();
            System.out.println((char) input);
        }
        catch (IOException e) {
            System.err.println("Error reading input: " + e.getMessage());
        }
    }
    static void readMultipleCharacters() {
        System.out.println("Enter characters (Press 'Enter' to quit):");
        try {
            int input;
            while ((input = System.in.read()) != '\n') {
                System.out.print((char) input);
            }
        } catch (IOException e) {
            System.err.println("Error reading input: " + e.getMessage());
        }
    }

    static void readWithParameters() {
        try {
            byte[] byteArray = new byte[5];
            int bytesRead;
            int totalBytesRead = 0;

            while ((bytesRead = System.in.read(byteArray, 0, byteArray.length)) != -1) {
                System.out.print("Data read: " + new String(byteArray, 0, bytesRead));
                totalBytesRead += bytesRead;
            }

            System.out.println("\nBytes Read: " + totalBytesRead);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
