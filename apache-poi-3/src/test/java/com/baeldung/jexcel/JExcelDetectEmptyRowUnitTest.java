package com.baeldung.jexcel;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import org.junit.Before;
import org.junit.Test;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;


public class JExcelDetectEmptyRowUnitTest {
	
	private JExcelHelper jexcelHelper;
    private static String FILE_NAME = "consumer_info_with_empty_row.xls";
    private String fileLocation;
    
    @Before
    public void loadExcelFile() throws IOException {
        
        fileLocation = "src/main/resources/" + FILE_NAME;
        jexcelHelper = new JExcelHelper();
    }
    
	@Test
    public void whenParsingJExcelFile_thenDetectEmptyRow() throws IOException, BiffException {
		Workbook workbook = Workbook.getWorkbook(new File(fileLocation));
        Sheet sheet = workbook.getSheet(0);
       
        Cell[] row = sheet.getRow(0);
        assertFalse(jexcelHelper.isRowEmpty(row));
        
        row = sheet.getRow(2);
        assertFalse(jexcelHelper.isRowEmpty(row));
        
        row = sheet.getRow(4);
        assertTrue(jexcelHelper.isRowEmpty(row)); // check the file consumer_info_with_empty_row.xls in resources folder, we have inserted empty row at the end
    }
}
