package com.baeldung.fastexcel;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.stream.Stream;

import org.dhatim.fastexcel.reader.ReadableWorkbook;
import org.dhatim.fastexcel.reader.Row;
import org.dhatim.fastexcel.reader.Sheet;
import org.junit.Before;
import org.junit.Test;


public class FastexcelDetectEmptyRowUnitTest {
	
	private FastexcelHelper fastexcelHelper;
    private static String FILE_NAME = "consumer_info_with_empty_row.xlsx";
    private String fileLocation;
    
    @Before
    public void loadExcelFile() throws IOException {
        
        fileLocation = "src/main/resources/" + FILE_NAME;
        fastexcelHelper = new FastexcelHelper();
    }
    
	@Test
    public void whenParsingFastExcelFile_thenDetectEmptyRow() throws IOException {
		System.out.print(fileLocation);
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
}
