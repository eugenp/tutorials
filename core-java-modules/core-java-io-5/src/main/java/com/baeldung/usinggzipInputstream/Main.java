package com.baeldung.usinggzipInputstream;

import java.io.*;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.stream.Stream;
import java.util.zip.GZIPInputStream;

public class Main {

    public static void main(String[] args) throws IOException {
        String filePath = Objects.requireNonNull(Main.class.getClassLoader().getResource("myFile.gz")).getFile();
        processGZipFile(filePath, lines -> lines.forEach(System.out::println));
    }

    public static Stream<String> readGZipFile(String filePath) throws IOException {
        InputStream inputStream = new FileInputStream(filePath);
        GZIPInputStream gzip = new GZIPInputStream(inputStream);
        BufferedReader br = new BufferedReader(new InputStreamReader(gzip));

        Stream<String> lines = br.lines();

        lines.onClose(() -> {
            try {
                br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        return lines;
    }

    public static void processGZipFile(String filePath, Consumer<Stream<String>> lineProcessor) throws IOException {
        try (Stream<String> lines = readGZipFile(filePath)) {
            lineProcessor.accept(lines);
        }
    }
}
