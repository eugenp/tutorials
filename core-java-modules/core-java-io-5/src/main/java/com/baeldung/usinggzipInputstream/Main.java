package com.baeldung.usinggzipInputstream;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.zip.GZIPInputStream;

public class Main {

    public static void main(String[] args) throws IOException {
        try (InputStream inputStream = Main.class.getClassLoader().getResourceAsStream("myFile.gz")) {
            if (inputStream == null) {
                throw new FileNotFoundException("File not found: myFile.gz");
            }

            processGZipFile(inputStream, lines -> lines.forEach(System.out::println));
        }
    }

    public static List<String> readGZipFile(String filePath) throws IOException {
        List<String> lines = new ArrayList<>();

        try (FileInputStream fileInputStream = new FileInputStream(filePath);
             GZIPInputStream gzipInputStream = new GZIPInputStream(fileInputStream);
             InputStreamReader inputStreamReader = new InputStreamReader(gzipInputStream);
             BufferedReader bufferedReader = new BufferedReader(inputStreamReader)) {

            String line;
            while ((line = bufferedReader.readLine()) != null) {
                lines.add(line);
            }
        }

        return lines;
    }

    public static void processGZipFile(InputStream inputStream, LineProcessor lineProcessor) throws IOException {
        try (Stream<String> lines = new BufferedReader(new InputStreamReader(new GZIPInputStream(inputStream)))
                .lines()
                .map(String::trim)) {
            lineProcessor.process(lines);
        }
    }

    @FunctionalInterface
    public interface LineProcessor {
        void process(Stream<String> lines);
    }
}
