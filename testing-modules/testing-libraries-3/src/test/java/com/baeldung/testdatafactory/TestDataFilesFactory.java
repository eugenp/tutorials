package com.baeldung.testdatafactory;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TestDataFilesFactory {
    public static String twoParagraphs() throws IOException {
        Path path = Paths.get("src", "test", "resources",
                "testdata", "twoParagraphs.txt");
        try (Stream<String> file = Files.lines(path)) {
            return file.collect(Collectors.joining("\n"));
        }
    }

    public static Document twoParagraphsAsDocument() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(
          Paths.get("src", "test", "resources",
            "testdata", "twoParagraphs.json").toFile(), Document.class);
    }
}
