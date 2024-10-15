package com.baeldung.emptyrow.jexcel;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import org.junit.Test;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;


public class JExcelDetectEmptyRowUnitTest {

    private JExcelHelper jexcelHelper = new JExcelHelper();
    private static final String EMPTY_FILE_PATH = "src/main/resources/empty_excel_file.xls";

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
