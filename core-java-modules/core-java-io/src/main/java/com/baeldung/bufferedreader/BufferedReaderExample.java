package com.baeldung.bufferedreader;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.stream.Collectors;

public class BufferedReaderExample {

    public String readAllLines(BufferedReader reader) throws IOException {
        StringBuilder content = new StringBuilder();
        String line;

        while ((line = reader.readLine()) != null) {
            content.append(line);
            content.append(System.lineSeparator());
        }

        return content.toString();
    }

    public String readAllLinesWithStream(BufferedReader reader) {
        return reader
                .lines()
                    .collect(Collectors.joining(System.lineSeparator()));
    }

    public String readAllCharsOneByOne(BufferedReader reader) throws IOException {
        StringBuilder content = new StringBuilder();

        int value;
        while ((value = reader.read()) != -1) {
            content.append((char) value);
        }

        return content.toString();
    }

    public String readMultipleChars(BufferedReader reader) throws IOException {
        int length = 5;
        char[] chars = new char[length];
        int charsRead = reader.read(chars, 0, length);

        String result;
        if (charsRead != -1) {
            result = new String(chars, 0,  charsRead);
        } else {
            result = "";
        }

        return result;
    }

    public String readFile() {
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader("src/main/resources/input.txt"));
            String content = readAllLines(reader);
            return content;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public String readFileTryWithResources() {
        try (BufferedReader reader = new BufferedReader(new FileReader("src/main/resources/input.txt"))) {
            String content = readAllLines(reader);
            return content;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

}
