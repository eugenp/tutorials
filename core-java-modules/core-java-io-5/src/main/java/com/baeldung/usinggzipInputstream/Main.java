package com.baeldung.usinggzipInputstream;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.stream.Stream;
import java.util.zip.GZIPInputStream;

import static java.util.stream.Collectors.toList;

public class Main {
    static String filePath = Objects.requireNonNull(Main.class.getClassLoader().getResource("myFile.gz")).getFile();

    public static void main(String[] args) throws IOException {
        // Test readGZipFile method
        List<String> fileContents = readGZipFile(filePath);
        System.out.println("Contents of GZIP file:");
        fileContents.forEach(System.out::println);

        // Test findInZipFile method
        String searchTerm = "Line 1 content";
        List<String> foundLines = findInZipFile(filePath, searchTerm);
        System.out.println("Lines containing '" + searchTerm + "' in GZIP file:");
        foundLines.forEach(System.out::println);


        // Test useContentsOfZipFile method
        System.out.println("Using contents of GZIP file with consumer:");
        useContentsOfZipFile(filePath, linesStream -> {
            linesStream.filter(line -> line.length() > 10).forEach(System.out::println);
        });
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

    public static List<String> findInZipFile(String filePath, String toFind) throws IOException {
        try (InputStream inputStream = new FileInputStream(filePath);
             GZIPInputStream gzipInputStream = new GZIPInputStream(inputStream);
             InputStreamReader inputStreamReader = new InputStreamReader(gzipInputStream);
             BufferedReader bufferedReader = new BufferedReader(inputStreamReader)) {

            return bufferedReader.lines().filter(line -> line.contains(toFind)).collect(toList());
        }
    }

    public static void useContentsOfZipFile(String filePath, Consumer<Stream<String>> consumer) throws IOException {
        try (InputStream inputStream = new FileInputStream(filePath);
             GZIPInputStream gzipInputStream = new GZIPInputStream(inputStream);
             InputStreamReader inputStreamReader = new InputStreamReader(gzipInputStream);
             BufferedReader bufferedReader = new BufferedReader(inputStreamReader)) {

            consumer.accept(bufferedReader.lines());
        }
    }

}
