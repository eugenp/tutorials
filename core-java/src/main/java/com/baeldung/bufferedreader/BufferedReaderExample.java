package com.baeldung.bufferedreader;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

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
        StringBuilder content = new StringBuilder();
        reader.lines()
            .forEach(line -> content.append(line)
                .append(System.lineSeparator()));
        return content.toString();
    }

    public String readAllCharacters(BufferedReader reader) throws IOException {
        StringBuilder content = new StringBuilder();

        int value;
        while ((value = reader.read()) != -1) {
            content.append((char) value);
        }

        return content.toString();
    }

    public String readAllCharactersUsingArray(BufferedReader reader) throws IOException {
        StringBuilder content = new StringBuilder();

        char[] buf = new char[512];
        int charsRead;

        while ((charsRead = reader.read(buf, 0, buf.length)) != -1) {
            content.append(buf, 0, charsRead);
        }

        return content.toString();
    }

    public String readWithSkipping(BufferedReader reader) throws IOException {
        StringBuilder content = new StringBuilder();

        int value;
        while ((value = reader.read()) != -1) {
            content.append((char) value);
            reader.skip(4);
        }

        return content.toString();
    }

    public String markAndReset(BufferedReader reader) throws IOException {
        StringBuilder content = new StringBuilder();

        reader.mark(512);

        for (int i = 0; i < 3; i++) {
            content.append(reader.readLine());
            reader.reset();
            reader.mark(512);
        }

        return content.toString();
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
