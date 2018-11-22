package com.baeldung.hexagonal.adapters;

import com.baeldung.hexagonal.ExtractorPort;

import java.util.Scanner;

public class ConsoleExtractorAdapter implements ExtractorPort {
    public byte[] extractData() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Write data to be transformed: ");

        String dataString = scanner.nextLine();
        return dataString.getBytes();
    }
}
