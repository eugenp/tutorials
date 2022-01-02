package com.baeldung.poi.excel.cellstyle;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Paths;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Before;
import org.junit.Test;

public class CellStylerUnitTest {
    private static String FILE_NAME = "com/baeldung/poi/excel/cellstyle/CellStyle.xlsx";
    private static final String NEW_FILE_NAME = "CellStyleTest_output.xlsx";
    private String fileLocation;

    @Before
    public void setup() throws IOException, URISyntaxException {
        fileLocation = Paths.get(ClassLoader.getSystemResource(FILE_NAME)
            .toURI())
            .toString();
    }

    @Test
    public void testApplyWarningColor() throws IOException {
        Workbook workbook = new XSSFWorkbook(fileLocation);
        Sheet sheet = workbook.getSheetAt(0);
        Row row1 = sheet.createRow(0);
        row1.setHeightInPoints((short) 40);

        CellStyler styler = new CellStyler();
        CellStyle style = styler.createWarningColor(workbook);

        Cell cell1 = row1.createCell(0);
        cell1.setCellStyle(style);
        cell1.setCellValue("Hello");

        Cell cell2 = row1.createCell(1);
        cell2.setCellStyle(style);
        cell2.setCellValue("world!");

        FileOutputStream outputStream = new FileOutputStream(NEW_FILE_NAME);
        workbook.write(outputStream);
        outputStream.close();
        workbook.close();
    }
}
