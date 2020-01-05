package com.baeldung.poi.excel;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Paths;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Before;
import org.junit.Test;

public class ExcelCellFormatterUnitTest {
    private static final String FILE_NAME = "ExcelCellFormatterTest.xlsx";
    private static final int STRING_CELL_INDEX = 0;
    private static final int BOOLEAN_CELL_INDEX = 1;
    private static final int RAW_NUMERIC_CELL_INDEX = 2;
    private static final int FORMATTED_NUMERIC_CELL_INDEX = 3;
    private static final int FORMULA_CELL_INDEX = 4;

    private String fileLocation;

    @Before
    public void setup() throws IOException, URISyntaxException {
            fileLocation = Paths.get(ClassLoader.getSystemResource(FILE_NAME).toURI()).toString();
    }

    @Test
    public void gvieStringCell_whenGetCellStringValue_thenReturnStringValue() throws IOException {
        Workbook workbook = new XSSFWorkbook(fileLocation);
        Sheet sheet = workbook.getSheetAt(0);
        Row row = sheet.getRow(0);

        ExcelCellFormatter formatter = new ExcelCellFormatter();
        assertEquals("String Test", formatter.getCellStringValue(row.getCell(STRING_CELL_INDEX)));
        workbook.close();
    }

    @Test
    public void gvieBooleanCell_whenGetCellStringValue_thenReturnBooleanStringValue() throws IOException {
        Workbook workbook = new XSSFWorkbook(fileLocation);
        Sheet sheet = workbook.getSheetAt(0);
        Row row = sheet.getRow(0);

        ExcelCellFormatter formatter = new ExcelCellFormatter();
        assertEquals("TRUE", formatter.getCellStringValue(row.getCell(BOOLEAN_CELL_INDEX)));
        workbook.close();
    }

    @Test
    public void gvieNumericCell_whenGetCellStringValue_thenReturnNumericStringValue() throws IOException {
        Workbook workbook = new XSSFWorkbook(fileLocation);
        Sheet sheet = workbook.getSheetAt(0);
        Row row = sheet.getRow(0);

        ExcelCellFormatter formatter = new ExcelCellFormatter();
        assertEquals("1.234", formatter.getCellStringValue(row.getCell(RAW_NUMERIC_CELL_INDEX)));
        assertEquals("1.23", formatter.getCellStringValue(row.getCell(FORMATTED_NUMERIC_CELL_INDEX)));
        workbook.close();
    }

    @Test
    public void gvieFormualCell_whenGetCellStringValue_thenReturnOriginalFormulaString() throws IOException {
        Workbook workbook = new XSSFWorkbook(fileLocation);
        Sheet sheet = workbook.getSheetAt(0);
        Row row = sheet.getRow(0);

        ExcelCellFormatter formatter = new ExcelCellFormatter();
        assertEquals("SUM(1+2)", formatter.getCellStringValue(row.getCell(FORMULA_CELL_INDEX)));
        workbook.close();
    }

    @Test
    public void gvieFormualCell_whenGetCellStringValueForFormula_thenReturnOriginalFormulatring() throws IOException {
        Workbook workbook = new XSSFWorkbook(fileLocation);
        Sheet sheet = workbook.getSheetAt(0);
        Row row = sheet.getRow(0);

        ExcelCellFormatter formatter = new ExcelCellFormatter();
        assertEquals("3", formatter.getCellStringValueWithFormula(row.getCell(FORMULA_CELL_INDEX), workbook));
        workbook.close();
    }

}