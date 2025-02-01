package com.baeldung.xlsxtocsv;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class CommonsCSVWriter {

    /**
     * Writes data to a CSV file using Apache Commons CSV.
     *
     * @param data     the data to write (list of string arrays)
     * @param filePath the path to the output CSV file
     * @throws IOException if an I/O error occurs
     */
    public static void writeCSV(List<String[]> data, String filePath) throws IOException {
        try (FileWriter fw = new FileWriter(filePath);
             CSVPrinter csvPrinter = new CSVPrinter(fw, CSVFormat.DEFAULT)) {
            for (String[] row : data) {
                csvPrinter.printRecord((Object[]) row);
            }
            csvPrinter.flush();
        }
    }
}
