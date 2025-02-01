package com.baeldung.xlsxtocsv;

import org.apache.poi.ss.usermodel.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class XLSXReader {

    /**
     * Opens an XLSX workbook.
     *
     * @param filePath the path to the XLSX file
     * @return a Workbook object representing the XLSX file
     * @throws IOException if an I/O error occurs
     */
    public static Workbook openWorkbook(String filePath) throws IOException {
        try (FileInputStream fis = new FileInputStream(filePath)) {
            return WorkbookFactory.create(fis);
        }
    }

    /**
     * Iterates over rows and columns to output them as a list of string arrays.
     *
     * @param filePath the path to the XLSX file
     * @return a list of string arrays representing the data
     * @throws IOException if an I/O error occurs
     */
    public static List<String[]> iterateAndPrepareData(String filePath) throws IOException {
        Workbook workbook = openWorkbook(filePath);
        Sheet sheet = workbook.getSheetAt(0); // Assuming we are reading from the first sheet

        List<String[]> data = new ArrayList<>();
        DataFormatter formatter = new DataFormatter();

        // Iterate through each row in the sheet
        for (Row row : sheet) {
            int lastCellNum = row.getLastCellNum();
            if (lastCellNum < 0) {
                continue; // Skip empty rows or rows with invalid data
            }
            String[] rowData = new String[lastCellNum];
            // Iterate through each cell in the row
            for (int cn = 0; cn < lastCellNum; cn++) {
                Cell cell = row.getCell(cn);
                rowData[cn] = cell == null ? "" : formatter.formatCellValue(cell);
            }
            // Add the row data to the list
            data.add(rowData);
        }
        workbook.close();
        return data;
    }
}
