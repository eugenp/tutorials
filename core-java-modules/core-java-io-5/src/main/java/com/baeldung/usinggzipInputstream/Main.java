package com.baeldung.usinggzipInputstream;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.zip.GZIPInputStream;

public class Main {
    static String filePath = Objects.requireNonNull(Main.class.getClassLoader().getResource("myFile.gz")).getFile();

    public static void main(String[] args) throws IOException {

        try {
            readGZipFileWithFilter(filePath, lines -> lines.forEach(System.out::println));
        } catch (FileNotFoundException e) {
            System.err.println(e.getMessage());
        }
    }

    public static List<String> readGZipFile(String filePath) throws IOException {
        List<String> lines = new ArrayList<>();
        try (InputStream inputStream = new FileInputStream(filePath);
             GZIPInputStream gzipInputStream = new GZIPInputStream(inputStream);
             InputStreamReader inputStreamReader = new InputStreamReader(gzipInputStream);
             BufferedReader bufferedReader = new BufferedReader(inputStreamReader)) {

            String line;
            while ((line = bufferedReader.readLine()) != null) {
                lines.add(line);
            }
        }

        return lines;
    }

    public static void readGZipFileWithFilter(String filePath, Consumer<List<String>> lineConsumer) throws IOException {
        lineConsumer.accept(readGZipFile(filePath));
    }
}
