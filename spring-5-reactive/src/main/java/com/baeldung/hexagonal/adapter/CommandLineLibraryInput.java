package com.baeldung.hexagonal.adapter;


import com.baeldung.hexagonal.application.LibraryInput;
import com.baeldung.hexagonal.doc.Adapter;

import java.util.Scanner;

@Adapter
public class CommandLineLibraryInput implements LibraryInput {

    private final Scanner scanner;

    public CommandLineLibraryInput() {
        this.scanner = new Scanner(System.in);
    }

    @Override
    public String getTitle() {
        return scanner.nextLine();
    }
}
