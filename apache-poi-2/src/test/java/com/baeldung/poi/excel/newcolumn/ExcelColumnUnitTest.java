package com.baeldung.poi.excel.newcolumn;

import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Paths;

import static org.junit.Assert.assertEquals;

public class ExcelColumnUnitTest {
    private static final String FILE_NAME = "newColumnTest.xlsx";
    private String fileLocation;

    @Before
    public void setup() throws URISyntaxException {
        fileLocation = Paths.get(ClassLoader.getSystemResource(FILE_NAME).toURI()).toString();
    }

    @Test
    public void givenExistingRows_whenAddNewColumn_thenRowColumnNumberIncreased() throws IOException {
        Workbook workbook = new XSSFWorkbook(fileLocation);
        Sheet sheet = workbook.getSheetAt(0);
        Row row = sheet.getRow(0);
        assertEquals(5, row.getLastCellNum());

        ExcelColumn excelColumn = new ExcelColumn();
        excelColumn.addColumn(sheet, CellType.STRING);
        assertEquals(6, row.getLastCellNum());

        workbook.close();
    }

}