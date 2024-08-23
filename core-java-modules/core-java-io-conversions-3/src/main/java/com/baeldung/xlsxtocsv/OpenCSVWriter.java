package com.baeldung.xlsxtocsv;

import com.opencsv.CSVWriter;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class OpenCSVWriter {
    public static void writeCSV(List<String[]> data, String filePath) throws IOException {
        try (FileWriter fw = new FileWriter(filePath);
             CSVWriter csvWriter = new CSVWriter(fw,
                     CSVWriter.DEFAULT_SEPARATOR,
                     CSVWriter.NO_QUOTE_CHARACTER,
                     CSVWriter.DEFAULT_ESCAPE_CHARACTER,
                     CSVWriter.DEFAULT_LINE_END)) {
            for (String[] row : data) {
                csvWriter.writeNext(row);
            }
        }
    }
}