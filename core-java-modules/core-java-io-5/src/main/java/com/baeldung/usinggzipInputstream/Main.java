package com.baeldung.usinggzipInputstream;

import java.io.*;
import java.util.Objects;
import java.util.stream.Stream;
import java.util.zip.GZIPInputStream;

public class Main {

    public static void main(String[] args) {
        String filePath = Objects.requireNonNull(Main.class.getClassLoader().getResource("myFile.gz")).getFile();
        try {
            try (Stream<String> lines = readGZipFile(filePath)) {
                lines.forEach(System.out::println);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
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

}
