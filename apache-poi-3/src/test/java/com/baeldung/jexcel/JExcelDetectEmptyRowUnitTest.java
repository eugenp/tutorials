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
    private static final String FILE_PATH = "src/main/resources/consumer_info_with_empty_row.xls";
    private static final String EMPTY_FILE_PATH = "src/main/resources/empty_excel_file.xls";

    @Before
    public void loadExcelFile() throws IOException {
        jexcelHelper = new JExcelHelper();
    }

    @Test
    public void givenXLSFile_whenParsingJExcelFile_thenDetectEmptyRow() throws IOException, BiffException {
        Workbook workbook = Workbook.getWorkbook(new File(FILE_PATH));
        Sheet sheet = workbook.getSheet(0);

        Cell[] row = sheet.getRow(0);
        assertFalse(jexcelHelper.isRowEmpty(row));

        row = sheet.getRow(2);
        assertFalse(jexcelHelper.isRowEmpty(row));

        row = sheet.getRow(4);
        assertTrue(jexcelHelper.isRowEmpty(row)); // check the file consumer_info_with_empty_row.xls in resources folder, we have inserted empty row at the end
    }

    @Test
    public void givenXLSFile_whenParsingJExcelFile_thenDetectAllRowsEmpty() throws IOException, BiffException {
        Workbook workbook = Workbook.getWorkbook(new File(EMPTY_FILE_PATH));
        Sheet sheet = workbook.getSheet(0);

        for (int rowNum = 0; rowNum < sheet.getRows(); rowNum++) {
            Cell[] row = sheet.getRow(rowNum);
            assertTrue(jexcelHelper.isRowEmpty(row));
        }
    }

}
