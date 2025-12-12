package com.baeldung.hssfworkbook;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.baeldung.hssfworkbook.ExcelCreator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ExcelCreatorTest {

    private static final String SHEET_NAME = "Employees";
    private static final String[] COLUMN_HEADERS = { "ID", "Name", "Department" };
    private static final Object[][] DATA = { { 101, "John Doe", "Finance" }, { 102, "Jane Smith", "HR" }, { 103, "Michael Clark", "IT" } };

    @Test
    @DisplayName("Workbook should be created and contain one sheet named 'Employees'")
    void givenExcelCreator_whenCreateSampleWorkbookCalled_thenWorkbookContainsOneSheetNamedEmployees() {
        HSSFWorkbook workbook = ExcelCreator.createSampleWorkbook();

        assertNotNull(workbook, "The workbook should not be null.");
        assertEquals(1, workbook.getNumberOfSheets(), "The workbook should contain exactly one sheet.");

        Sheet sheet = workbook.getSheet(SHEET_NAME);
        assertNotNull(sheet, "The sheet named '" + SHEET_NAME + "' must exist.");
    }

    @Test
    @DisplayName("Header row should have correct content and bold style")
    void givenExcelCreator_whenCreateSampleWorkbookCalled_thenHeaderRowContainsCorrectTextAndBoldStyle() {
        HSSFWorkbook workbook = ExcelCreator.createSampleWorkbook();
        Sheet sheet = workbook.getSheet(SHEET_NAME);

        Row headerRow = sheet.getRow(0);
        assertNotNull(headerRow, "Header row (row 0) should exist.");

        Font expectedFont = workbook.getFontAt(headerRow.getCell(0)
          .getCellStyle()
          .getFontIndex());
        assertTrue(expectedFont.getBold(), "Header font should be bold.");

        for (int i = 0; i < COLUMN_HEADERS.length; i++) {
            Cell cell = headerRow.getCell(i);
            assertNotNull(cell, "Header cell at index " + i + " should exist.");
            assertEquals(COLUMN_HEADERS[i], cell.getStringCellValue(), "Header text mismatch at column " + i + ".");

            CellStyle cellStyle = cell.getCellStyle();
            assertNotNull(cellStyle, "Header cell should have a style applied.");

            Font actualFont = workbook.getFontAt(cellStyle.getFontIndex());
            assertTrue(actualFont.getBold(), "Header cell at index " + i + " style must be bold.");
        }
    }

    @Test
    @DisplayName("Data rows should have correct content and data type handling")
    void givenExcelCreator_whenCreateSampleWorkbookCalled_thenDataRowsContainCorrectContentAndTypes() {
        HSSFWorkbook workbook = ExcelCreator.createSampleWorkbook();
        Sheet sheet = workbook.getSheet(SHEET_NAME);

        assertEquals(DATA.length + 1, sheet.getLastRowNum() + 1, "The sheet should contain header row + data rows.");

        for (int i = 0; i < DATA.length; i++) {
            Row dataRow = sheet.getRow(i + 1);
            Object[] expectedRowData = DATA[i];

            assertNotNull(dataRow, "Data row at index " + (i + 1) + " should exist.");

            for (int j = 0; j < expectedRowData.length; j++) {
                Cell cell = dataRow.getCell(j);
                Object expectedValue = expectedRowData[j];

                assertNotNull(cell, "Cell [" + (i + 1) + ", " + j + "] should exist.");

                if (expectedValue instanceof Integer) {
                    assertEquals(CellType.NUMERIC, cell.getCellType(), "Cell " + j + " type should be NUMERIC for Integer.");
                    assertEquals(((Integer) expectedValue).doubleValue(), cell.getNumericCellValue(), 0.001, "Numeric value mismatch.");
                } else {
                    assertEquals(CellType.STRING, cell.getCellType(), "Cell " + j + " type should be STRING.");
                    assertEquals(expectedValue.toString(), cell.getStringCellValue(), "String value mismatch.");
                }
            }
        }
    }
}