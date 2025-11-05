package com.baeldung.xlsxtocsv;

import java.io.IOException;
import java.util.List;

public class ConvertToCSV {

    /**
     * Converts data from an XLSX file to CSV format using Apache Commons CSV.
     *
     * @param xlsxFilePath the path to the XLSX file
     * @param csvFilePath  the path to the output CSV file
     * @throws IOException if an I/O error occurs
     */
    public static void convertWithCommonsCSV(String xlsxFilePath, String csvFilePath) throws IOException {
        // Step 1: Extract data from XLSX file
        List<String[]> data = XLSXReader.iterateAndPrepareData(xlsxFilePath);
        // Step 2: Write data to CSV file using Apache Commons CSV
        CommonsCSVWriter.writeCSV(data, csvFilePath);
    }

    /**
     * Converts data from an XLSX file to CSV format using OpenCSV.
     *
     * @param xlsxFilePath the path to the XLSX file
     * @param csvFilePath  the path to the output CSV file
     * @throws IOException if an I/O error occurs
     */
    public static void convertWithOpenCSV(String xlsxFilePath, String csvFilePath) throws IOException {
        // Step 1: Extract data from XLSX file
        List<String[]> data = XLSXReader.iterateAndPrepareData(xlsxFilePath);
        // Step 2: Write data to CSV file using OpenCSV
        OpenCSVWriter.writeCSV(data, csvFilePath);
    }
}