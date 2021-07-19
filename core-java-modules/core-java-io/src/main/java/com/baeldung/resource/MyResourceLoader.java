package com.baeldung.resource;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.stream.Collectors;

public class MyResourceLoader {

    private void loadFileWithReader() throws IOException {

        try (FileReader fileReader = new FileReader("src/main/resources/input.txt");
             BufferedReader reader = new BufferedReader(fileReader)) {
            String contents = reader.lines()
                .collect(Collectors.joining(System.lineSeparator()));
            System.out.println(contents);
        }

    }

    private void loadFileAsResource() throws IOException {

        try (InputStream inputStream = getClass().getResourceAsStream("/input.txt");
             BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            String contents = reader.lines()
                .collect(Collectors.joining(System.lineSeparator()));
            System.out.println(contents);
        }
    }

    public static void main(String[] args) throws IOException {

        MyResourceLoader resourceLoader = new MyResourceLoader();

        resourceLoader.loadFileAsResource();
        resourceLoader.loadFileWithReader();

    }

}
