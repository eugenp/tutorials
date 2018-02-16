package com.baeldung.opencsv.opencsv;

import com.baeldung.opencsv.helpers.Helpers;
import com.baeldung.opencsv.pojos.CsvTransfer;
import com.opencsv.CSVReader;

import java.io.Reader;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class CsvReaderExamples {

    public static CompletableFuture<List<String[]>> readAll(Reader reader) {
        CompletableFuture<List<String[]>> future = new CompletableFuture<>();

        try {
            CSVReader csvReader = new CSVReader(reader);
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
            CSVReader csvReader = new CSVReader(reader);
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
