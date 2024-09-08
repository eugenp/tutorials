package com.baeldung.fastexcel;

import org.dhatim.fastexcel.reader.ReadableWorkbook;
import org.dhatim.fastexcel.reader.Row;
import org.dhatim.fastexcel.reader.Sheet;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class FastexcelIntegrationTest {

    private FastexcelHelper fastexcelHelper;
    private static String FILE_NAME = "fastexcel.xlsx";
    private String fileLocation;

    @Before
    public void generateExcelFile() throws IOException {

        File currDir = new File(".");
        String path = currDir.getAbsolutePath();
        fileLocation = path.substring(0, path.length() - 1) + FILE_NAME;

        fastexcelHelper = new FastexcelHelper();
        fastexcelHelper.writeExcel();
    }

    @Test
    public void whenParsingExcelFile_thenCorrect() throws IOException {
        Map<Integer, List<String>> data = fastexcelHelper.readExcel(fileLocation);

        assertEquals("Name", data.get(1).get(0));
        assertEquals("Age", data.get(1).get(1));

        assertEquals("John Smith", data.get(3).get(0));
        assertEquals("20", data.get(3).get(1));
    }
    
    @Test
    public void whenParsingFastExcelFile_thenDetectEmptyRow() throws IOException {
    	try (FileInputStream file = new FileInputStream(fileLocation); ReadableWorkbook wb = new ReadableWorkbook(file)) {
            Sheet sheet = wb.getFirstSheet();
            try (Stream<Row> rows = sheet.openStream()) {
            	Row lastRow = null;
                Iterator<Row> rowIterator = rows.iterator();
                // assert that last row is empty
                while (rowIterator.hasNext()) {
                    lastRow = rowIterator.next();
                }
                assertTrue(fastexcelHelper.isRowEmpty(lastRow));
            }
        }
    }
    
    @After
    public void cleanup() {
        File testFile = new File(fileLocation);
        if (testFile.exists()) {
            testFile.delete();
        }
    }
}
