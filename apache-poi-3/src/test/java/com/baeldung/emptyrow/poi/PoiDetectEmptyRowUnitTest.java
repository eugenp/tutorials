package com.baeldung.emptyrow.poi;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PoiDetectEmptyRowUnitTest {
    
    private PoiHelper poiHelper;
    private static final String XLS_EMPTY_FILE_PATH = "src/main/resources/empty_excel_file.xls";
    private static final String XLSX_EMPTY_FILE_PATH = "src/main/resources/empty_excel_file.xlsx";
    
    @Before
    public void loadExcelFile() throws IOException {
        poiHelper = new PoiHelper();
    }
    
    @Test
    public void givenXLSXFile_whenParsingExcelFile_thenDetectAllRowsEmpty() throws IOException {
        Workbook workbook = poiHelper.openWorkbook(XLSX_EMPTY_FILE_PATH);
        Sheet sheet = workbook.getSheetAt(0);

        for (int rowNum = 0; rowNum <= sheet.getLastRowNum(); rowNum++) {
            Row row = sheet.getRow(rowNum);
            assertTrue(poiHelper.isRowEmpty(row));
        }
    }
    
    @Test
    public void givenXLSFile_whenParsingExcelFile_thenDetectAllRowsEmpty() throws IOException {
        Workbook workbook = poiHelper.openWorkbook(XLS_EMPTY_FILE_PATH);
        Sheet sheet = workbook.getSheetAt(0);

        for (int rowNum = 0; rowNum <= sheet.getLastRowNum(); rowNum++) {
            Row row = sheet.getRow(rowNum);
            assertTrue(poiHelper.isRowEmpty(row));
        }
    }
}
