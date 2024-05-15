package com.baeldung.exception.exceptions_vs_errors;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class CheckedExceptionExcample {
    public static void main(String[] args) {
        try (FileInputStream fis = new FileInputStream(new File("test.txt"))) {
            fis.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
