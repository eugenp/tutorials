package com.baeldung.bufferedreader;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

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

    public String readAllLines2(BufferedReader reader) {
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

    public String readAllCharacters2(BufferedReader reader) throws IOException {
        StringBuilder content = new StringBuilder();

        String title = "text: ";
        char[] buf = Arrays.copyOf(title.toCharArray(), 512);
        int offset = title.length();

        int charsRead;

        while ((charsRead = reader.read(buf, offset, buf.length - offset)) != -1) {
            content.append(buf, 0, offset + charsRead);
            content.append(" --- ");
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

    public void markAndReset(BufferedReader reader) throws IOException {
        reader.mark(512);

        for (int i = 0; i < 3; i++) {
            System.out.println(reader.readLine());
            reader.reset();
            reader.mark(512);
        }
    }

    public void readFile() {
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader("src/main/resources/input.txt"));
            String content = readAllLines(reader);
            System.out.println(content);
        } catch (IOException e) {
            e.printStackTrace();
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

    public void readFileTryWithResources() {
        try (BufferedReader reader = new BufferedReader(new FileReader("src/main/resources/input.txt"))) {
            String content = readAllLines(reader);
            System.out.println(content);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
