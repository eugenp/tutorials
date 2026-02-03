package com.baeldung.libraries.opencsv.examples.sync;

import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;

import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class CsvReaderExamples {

    public static List<String[]> readAllLines(Path filePath) throws Exception {

        CSVParser parser = new CSVParserBuilder()
          .withSeparator(',')
          .withIgnoreQuotations(true)
          .build();

        try (Reader reader = Files.newBufferedReader(filePath)) {
            CSVReaderBuilder csvReaderBuilder = new CSVReaderBuilder(reader)
              .withSkipLines(0)
              .withCSVParser(parser);

            try (CSVReader csvReader = csvReaderBuilder.build()) {
                return csvReader.readAll();
            }
        }

    }

    public static List<String[]> readLineByLine(Path filePath) throws Exception {
        List<String[]> list = new ArrayList<>();

        CSVParser parser = new CSVParserBuilder()
          .withSeparator(',')
          .withIgnoreQuotations(true)
          .build();

        try (Reader reader = Files.newBufferedReader(filePath)) {

            CSVReaderBuilder csvReaderBuilder = new CSVReaderBuilder(reader)
              .withSkipLines(0)
              .withCSVParser(parser);

            try (CSVReader csvReader = csvReaderBuilder.build()) {
                String[] line;
                while ((line = csvReader.readNext()) != null) {
                    list.add(line);
                }
            }

        }
        return list;
    }

}
