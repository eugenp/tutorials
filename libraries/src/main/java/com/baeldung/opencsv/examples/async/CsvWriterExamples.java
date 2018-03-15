package com.baeldung.opencsv.examples;

import com.baeldung.opencsv.helpers.Helpers;
import com.opencsv.CSVWriter;

import java.io.FileWriter;
import java.nio.file.Path;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class CsvWriterExamples {

    public static CompletableFuture csvWriterOneByOne(List<String[]> stringArray, Path path) {
        try {
            CSVWriter writer = new CSVWriter(new FileWriter(path.toString()));
            for (String[] array : stringArray) {
                writer.writeNext(array);
            }
            writer.close();
        } catch (Exception ex) {
            Helpers.err(ex);
        }
        return CompletableFuture.completedFuture(Helpers.readFile(path));
    }

    public static CompletableFuture csvWriterAll(List<String[]> stringArray, Path path) {
        try {
            CSVWriter writer = new CSVWriter(new FileWriter(path.toString()));
            writer.writeAll(stringArray);
            writer.close();
        } catch (Exception ex) {
            Helpers.err(ex);
        }
        return CompletableFuture.completedFuture(Helpers.readFile(path));
    }
}