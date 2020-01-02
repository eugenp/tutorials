package com.baeldung.poi.excel;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ExcelCellFormatterIntegrationTest {
    private static int STRING_CELL_INDEX = 0;
    private static int BOOLEAN_CELL_INDEX = 1;
    private static int RAW_NUMERIC_CELL_INDEX = 2;
    private static int FORMATTED_NUMERIC_CELL_INDEX = 3;
    private static int FORMULA_CELL_INDEX = 4;

    private String fileLocation;

    @Before
    public void generateExcelFile() throws IOException {

        Workbook workbook = new XSSFWorkbook();

        Sheet sheet = workbook.createSheet("Test");
        Row row = sheet.createRow(0);
        Cell cell = row.createCell(STRING_CELL_INDEX);
        cell.setCellValue("String Test"); // STRING cell

        cell = row.createCell(BOOLEAN_CELL_INDEX);
        cell.setCellValue(true); // BOOLEAN cell

        cell = row.createCell(RAW_NUMERIC_CELL_INDEX);
        cell.setCellValue(1234.5678); // NUMERIC cell

        cell = row.createCell(FORMATTED_NUMERIC_CELL_INDEX);
        cell.setCellValue(1234.5678);
        CellStyle curStyle = workbook.createCellStyle();
        DataFormat df = workbook.createDataFormat();
        curStyle.setDataFormat(df.getFormat("$#,##0.00"));
        cell.setCellStyle(curStyle); // NUMERIC cell with format rule

        cell = row.createCell(FORMULA_CELL_INDEX);
        cell.setCellFormula("SUM(C1:D1)"); // FORMULA cell

        File tempFile = File.createTempFile("ExcelCellFormatterIntegrationTest", ".xlsx");

        fileLocation = tempFile.getAbsolutePath();

        FileOutputStream outputStream = new FileOutputStream(fileLocation);
        workbook.write(outputStream);
        workbook.close();
        outputStream.close();
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
        assertEquals("1234.5678", formatter.getCellStringValue(row.getCell(RAW_NUMERIC_CELL_INDEX)));
        assertEquals("$1,234.57", formatter.getCellStringValue(row.getCell(FORMATTED_NUMERIC_CELL_INDEX)));
        workbook.close();
    }

    @Test
    public void gvieFormualCell_whenGetCellStringValue_thenReturnOriginalFormulaString() throws IOException {
        Workbook workbook = new XSSFWorkbook(fileLocation);
        Sheet sheet = workbook.getSheetAt(0);
        Row row = sheet.getRow(0);

        ExcelCellFormatter formatter = new ExcelCellFormatter();
        assertEquals("SUM(C1:D1)", formatter.getCellStringValue(row.getCell(FORMULA_CELL_INDEX)));
        workbook.close();
    }

    @Test
    public void gvieFormualCell_whenGetCellStringValueForFormula_thenReturnOriginalFormulatring() throws IOException {
        Workbook workbook = new XSSFWorkbook(fileLocation);
        Sheet sheet = workbook.getSheetAt(0);
        Row row = sheet.getRow(0);

        ExcelCellFormatter formatter = new ExcelCellFormatter();
        assertEquals("2469.1356", formatter.getCellStringValueWithFormula(row.getCell(FORMULA_CELL_INDEX), workbook));
        workbook.close();
    }

    @After
    public void cleanup() {
        File testFile = new File(fileLocation);
        if (testFile.exists()) {
            testFile.deleteOnExit();
        }
    }
}