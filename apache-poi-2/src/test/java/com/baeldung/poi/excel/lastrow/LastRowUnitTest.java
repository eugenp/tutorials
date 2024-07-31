package com.baeldung.poi.excel.lastrow;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

public class LastRowUnitTest {
    private static final String FILE_NAME = "lastRowTest.xlsx";
    private String fileLocation;

    @Before
    public void setup() throws URISyntaxException {
        fileLocation = Paths.get(ClassLoader.getSystemResource(FILE_NAME).toURI()).toString();
    }

    @Test
    public void givenExampleGrid_whenGetRow_thenReturnRowObjectIfModified() throws IOException {
        Workbook workbook = new XSSFWorkbook(fileLocation);
        Sheet sheet = workbook.getSheetAt(0);

        assertEquals(7, sheet.getLastRowNum());
        assertEquals(6, sheet.getPhysicalNumberOfRows());

        assertNotNull(sheet.getRow(0));
        assertNotNull(sheet.getRow(1));
        assertNotNull(sheet.getRow(2));
        assertNotNull(sheet.getRow(3));
        assertNull(sheet.getRow(4));
        assertNull(sheet.getRow(5));
        assertNotNull(sheet.getRow(6));
        assertNotNull(sheet.getRow(7));
        assertNull(sheet.getRow(8));

        assertSame(sheet.getRow(7), getLastRowFromSheet(sheet));
    }

    @Test
    public void givenEmptySheet_whenGetRow_thenReturnNull() throws IOException {
        Workbook workbook = new XSSFWorkbook(fileLocation);
        Sheet sheet = workbook.getSheetAt(1);

        assertEquals(-1, sheet.getLastRowNum());
        assertEquals(0, sheet.getPhysicalNumberOfRows());

        assertNull(sheet.getRow(0));

        assertSame(sheet.getRow(0), getLastRowFromSheet(sheet));
    }

    public static Row getLastRowFromSheet(Sheet sheet) {
        Row lastRow = null;
        int lastRowNum = sheet.getLastRowNum();
        if (lastRowNum >= 0) {
            lastRow = sheet.getRow(lastRowNum);
        }
        return lastRow;
    }
}
