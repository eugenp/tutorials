package com.baeldung.poi.columnnames;


import org.apache.poi.ss.usermodel.*;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ExcelUtilsUnitTest {

    private static final String XLS_TEST_FILE_PATH = "src/main/resources/consumer_info.xls";
    private static final String XLSX_TEST_FILE_PATH = "src/main/resources/food_info.xlsx";
    private static final String SHEET_NAME = "Sheet1";

    @Test
    public void givenExcelFileWithXLSXFormat_whenGetColumnNames_thenReturnsColumnNames() throws IOException {
        Workbook workbook = ExcelUtils.openWorkbook(XLSX_TEST_FILE_PATH);
        Sheet sheet = ExcelUtils.getSheet(workbook, SHEET_NAME);
        List<String> columnNames = ExcelUtils.getColumnNames(sheet);

        assertEquals(4, columnNames.size());
        assertTrue(columnNames.contains("Category"));
        assertTrue(columnNames.contains("Name"));
        assertTrue(columnNames.contains("Measure"));
        assertTrue(columnNames.contains("Calories"));
        workbook.close();
    }

    @Test
    public void givenExcelFileWithXLSFormat_whenGetColumnNames_thenReturnsColumnNames() throws IOException {
        Workbook workbook = ExcelUtils.openWorkbook(XLS_TEST_FILE_PATH);
        Sheet sheet = ExcelUtils.getSheet(workbook, SHEET_NAME);
        List<String> columnNames = ExcelUtils.getColumnNames(sheet);

        assertEquals(3, columnNames.size());
        assertTrue(columnNames.contains("Name"));
        assertTrue(columnNames.contains("Age"));
        assertTrue(columnNames.contains("City"));

        workbook.close();
    }
}
