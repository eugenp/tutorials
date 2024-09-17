package com.baeldung.poi.excel;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ExcelIntegrationTest {

    private ExcelPOIHelper excelPOIHelper;
    private static String FILE_NAME = "temp.xlsx";
    private String fileLocation;

    @Before
    public void generateExcelFile() throws IOException {

        File currDir = new File(".");
        String path = currDir.getAbsolutePath();
        fileLocation = path.substring(0, path.length() - 1) + FILE_NAME;

        excelPOIHelper = new ExcelPOIHelper();
        excelPOIHelper.writeExcel();

    }

    @Test
    public void whenParsingPOIExcelFile_thenCorrect() throws IOException {
        Map<Integer, List<String>> data = excelPOIHelper.readExcel(fileLocation);

        assertEquals("Name", data.get(0)
            .get(0));
        assertEquals("Age", data.get(0)
            .get(1));

        assertEquals("John Smith", data.get(1)
            .get(0));
        assertEquals("20", data.get(1)
            .get(1));
    }

    @After
    public void cleanup(){
        File testFile = new File(fileLocation);
        if (testFile.exists()) {
           testFile.delete();     
        }
    }
}