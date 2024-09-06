package com.baeldung.fileparser;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FilesLinesExample {

    static ArrayList<String> generateArrayListFromFile(String filename) throws IOException {

        try (Stream<String> lines = Files.lines(Paths.get(filename))) {
            return lines.collect(Collectors.toCollection(ArrayList::new));
        }

    }

}
