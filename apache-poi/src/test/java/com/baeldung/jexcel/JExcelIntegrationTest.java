package com.baeldung.jexcel;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import jxl.write.WriteException;

public class JExcelIntegrationTest {

    private JExcelHelper jExcelHelper;
    private static String FILE_NAME = "temp.xls";
    private String fileLocation;

    @Before
    public void generateExcelFile() throws IOException, WriteException {

        File currDir = new File(".");
        String path = currDir.getAbsolutePath();
        fileLocation = path.substring(0, path.length() - 1) + FILE_NAME;

        jExcelHelper = new JExcelHelper();
        jExcelHelper.writeJExcel();

    }

    @Test
    public void whenParsingJExcelFile_thenCorrect() throws IOException, BiffException {
        Map<Integer, List<String>> data = jExcelHelper.readJExcel(fileLocation);

        assertEquals("Name", data.get(0)
            .get(0));
        assertEquals("Age", data.get(0)
            .get(1));

        assertEquals("John Smith", data.get(2)
            .get(0));
        assertEquals("20", data.get(2)
            .get(1));
    }
    
    @Test
    public void whenParsingJExcelFile_thenDetectEmptyRow() throws IOException, BiffException {
    	Workbook workbook = Workbook.getWorkbook(new File(fileLocation));
        Sheet sheet = workbook.getSheet(0);
       
        Cell[] row = sheet.getRow(0);
        assertFalse(jExcelHelper.isRowEmpty(row)); // check writeJExcel() method, we have inserted a row
        
        row = sheet.getRow(2);
        assertFalse(jExcelHelper.isRowEmpty(row));
        
        row = sheet.getRow(3);
        assertTrue(jExcelHelper.isRowEmpty(row)); // check writeJExcel() method, we have inserted "" in this row
    }

    @After
    public void cleanup(){
        File testFile = new File(fileLocation);
        if (testFile.exists()) {
           testFile.delete();     
        }
    }
}