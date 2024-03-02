package com.baeldung.usinggzipInputstream;

import java.io.*;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.zip.GZIPInputStream;

public class Main {
    public static void main(String[] args) {
        String filePath = Objects.requireNonNull(Main.class.getClassLoader().getResource("myFile.gz")).getFile();
        try {
            List<String> lines = readGZipFile(filePath);
            lines.forEach(System.out::println);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<String> readGZipFile(String filePath) throws IOException {
        try (InputStream inputStream = new FileInputStream(filePath);
             GZIPInputStream gzip = new GZIPInputStream(inputStream);
             BufferedReader br = new BufferedReader(new InputStreamReader(gzip))) {

            return br.lines().collect(Collectors.toList());

        }
    }
}
