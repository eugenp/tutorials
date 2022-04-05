package com.baeldung.poi.excel.multilinetext;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Paths;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Before;
import org.junit.Test;

public class MultilineTextUnitTest {
    private static String FILE_NAME = "com/baeldung/poi/excel/multilinetext/MultilineTextTest.xlsx";
    private static final String NEW_FILE_NAME = "MultilineTextTest_output.xlsx";
    private static final int STRING_ROW_INDEX = 1;
    private static final int STRING_CELL_INDEX = 0;

    private String fileLocation;

    @Before
    public void setup() throws IOException, URISyntaxException {
        fileLocation = Paths.get(ClassLoader.getSystemResource(FILE_NAME)
            .toURI())
            .toString();
    }

    @Test
    public void givenMultilineTextCell_whenFormated_thenMultilineTextVisible() throws IOException {
        Workbook workbook = new XSSFWorkbook(fileLocation);
        Sheet sheet = workbook.getSheetAt(0);
        sheet.createRow(STRING_ROW_INDEX);
        Row row = sheet.getRow(STRING_ROW_INDEX);
        Cell cell = row.createCell(STRING_CELL_INDEX);

        cell.setCellValue("Hello \n world!");
        MultilineText multilineText = new MultilineText();
        multilineText.formatMultilineText(cell, STRING_CELL_INDEX);

        FileOutputStream outputStream = new FileOutputStream(NEW_FILE_NAME);
        workbook.write(outputStream);
        outputStream.close();

        File file = new File(NEW_FILE_NAME);
        FileInputStream fileInputStream = new FileInputStream(file);
        Workbook testWorkbook = new XSSFWorkbook(fileInputStream);
        assertTrue(row.getHeightInPoints() == testWorkbook.getSheetAt(0)
            .getRow(STRING_ROW_INDEX)
            .getHeightInPoints());
        DataFormatter formatter = new DataFormatter();
        assertEquals("Hello \n world!", formatter.formatCellValue(testWorkbook.getSheetAt(0)
            .getRow(STRING_ROW_INDEX)
            .getCell(STRING_CELL_INDEX)));
        testWorkbook.close();
        fileInputStream.close();
        file.delete();

        workbook.close();
    }
}
