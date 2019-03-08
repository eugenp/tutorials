package com.baeldung.hexagonal;

import java.io.BufferedWriter;
import java.io.IOException;

public class FileWriter implements Writer {

    private static final String FILENAME = "E:\\test\\filename.txt";

    @Override
    public void write(String text) {
        try (BufferedWriter bw = new BufferedWriter(new java.io.FileWriter(FILENAME))) {
            bw.write(text);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}