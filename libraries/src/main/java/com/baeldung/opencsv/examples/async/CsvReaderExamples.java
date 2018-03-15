package com.baeldung.opencsv.examples;

import com.baeldung.opencsv.helpers.Helpers;
import com.baeldung.opencsv.pojos.CsvTransfer;
import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;

import java.io.Reader;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class CsvReaderExamples {

    public static CompletableFuture<List<String[]>> readAll(Reader reader) {
        CompletableFuture<List<String[]>> future = new CompletableFuture<>();
        try {
            CSVParser parser = new CSVParserBuilder()
                    .withSeparator(',')
                    .withIgnoreQuotations(true)
                    .build();

            CSVReader csvReader = new CSVReaderBuilder(reader)
                    .withSkipLines(0)
                    .withCSVParser(parser)
                    .build();

            future.complete(csvReader.readAll());
            reader.close();
            csvReader.close();
        } catch (Exception ex) {
            Helpers.err(ex);
        }
        return future;
    }

    public static CompletableFuture<List<String[]>> oneByOne(Reader reader) {
        CompletableFuture<List<String[]>> future = new CompletableFuture<>();
        CsvTransfer csvTransfer = new CsvTransfer();
        try {
            CSVParser parser = new CSVParserBuilder()
                    .withSeparator(',')
                    .withIgnoreQuotations(true)
                    .build();

            CSVReader csvReader = new CSVReaderBuilder(reader)
                    .withSkipLines(0)
                    .withCSVParser(parser)
                    .build();

            String[] line;
            while ((line = csvReader.readNext()) != null) {
                csvTransfer.addLine(line);
            }
            reader.close();
            csvReader.close();
            future.complete(csvTransfer.getCsvStringList());
        } catch (Exception ex) {
            Helpers.err(ex);
        }
        return future;
    }

}
