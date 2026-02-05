package com.baeldung.libraries.opencsv.examples.sync;

import com.baeldung.libraries.opencsv.helpers.Helpers;
import com.opencsv.CSVWriter;

import java.io.FileWriter;
import java.nio.file.Path;
import java.util.List;

public class CsvWriterExamples {

    public static String writeLineByLine(List<String[]> lines, Path path) throws Exception {

        try (CSVWriter writer = new CSVWriter(new FileWriter(path.toString()))) {
            for (String[] line : lines) {
                writer.writeNext(line);
            }
        }

        return Helpers.readFile(path);
    }

    public static String writeAllLines(List<String[]> lines, Path path) throws Exception {

        try (CSVWriter writer = new CSVWriter(new FileWriter(path.toString()))) {
            writer.writeAll(lines);
        }
        return Helpers.readFile(path);
    }
}