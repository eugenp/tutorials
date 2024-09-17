package com.baeldung.poi.columnnames;


import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ExcelUtilsUnitTest {

	private static final String XLS_TEST_FILE_PATH = "src/main/resources/consumer_info.xls";
	private static final String XLSX_TEST_FILE_PATH = "src/main/resources/food_info.xlsx";
	private static final String XLS_EMPTY_ROW_FILE_PATH = "src/main/resources/consumer_info_with_empty_row.xls";
	private static final String XLSX_EMPTY_ROW_FILE_PATH = "src/main/resources/consumer_info_with_empty_row.xlsx";
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
	
	@Test
    public void givenXLSFile_whenParsingExcelFile_thenDetectEmptyRow() throws IOException {
    	Workbook workbook = ExcelUtils.openWorkbook(XLS_EMPTY_ROW_FILE_PATH);
    	Sheet sheet = workbook.getSheetAt(0);
    	
    	Row row = sheet.getRow(0); // We have entered data in this row
        assertFalse(ExcelUtils.isRowEmpty(row));
        
        row = sheet.getRow(2); // We have entered data in this row
        assertFalse(ExcelUtils.isRowEmpty(row));
        
        row = sheet.getRow(5); // It is an empty row 
        assertTrue(ExcelUtils.isRowEmpty(row));
    }
	
	@Test
    public void givenXLSXFile_whenParsingExcelFile_thenDetectEmptyRow() throws IOException {
    	Workbook workbook = ExcelUtils.openWorkbook(XLSX_EMPTY_ROW_FILE_PATH);
    	Sheet sheet = workbook.getSheetAt(0);
    	
    	Row row = sheet.getRow(0); // We have entered data in this row
        assertFalse(ExcelUtils.isRowEmpty(row));
        
        row = sheet.getRow(2); // We have entered data in this row
        assertFalse(ExcelUtils.isRowEmpty(row));
        
        row = sheet.getRow(5); // It is an empty row 
        assertTrue(ExcelUtils.isRowEmpty(row));
    }
}
