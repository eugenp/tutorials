package com.baeldung.streams.closure;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.stream.Stream;

/**
 * Contains a couple of simple stream API usages.
 */
public class StreamClosureSnippets {

    public static void main(String[] args) throws IOException {
        // Collection based streams shouldn't be closed
        Arrays.asList("Red", "Blue", "Green")
          .stream()
          .filter(c -> c.length() > 4)
          .map(String::toUpperCase)
          .forEach(System.out::print);

        String[] colors = {"Red", "Blue", "Green"};
        Arrays.stream(colors).map(String::toUpperCase).forEach(System.out::println);

        // IO-Based Streams Should be Closed via Try with Resources
        try (Stream<String> lines = Files.lines(Paths.get("/path/tp/file"))) {
            // lines will be closed after exiting the try block
        }
    }
}
