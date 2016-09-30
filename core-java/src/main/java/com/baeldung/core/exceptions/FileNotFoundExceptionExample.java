package com.baeldung.core.exceptions;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class FileNotFoundExceptionExample {

    public static void main(String[] args) throws IOException {
        BufferedReader rd = null;
        try {
            rd = new BufferedReader(new FileReader(new File("notExisting")));
            rd.readLine();
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }
    }
}