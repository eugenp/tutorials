package com.baeldung.exception.eof;

import java.io.DataInputStream;
import java.io.InputStream;
import java.util.Scanner;
import java.io.ByteArrayInputStream;

public class EOFExceptionDemo3 {
    public static void readInput() {
        InputStream is = new ByteArrayInputStream("1 2 3".getBytes());
        Scanner sc = new Scanner(is);
        while (sc.hasNextInt()) {
            int value = sc.nextInt();
            System.out.println("Input value: " + value);
        }
        System.out.println("End of file");
        sc.close();
    }
}