package com.baeldung.emptyrow.poi;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.junit.Test;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.FileInputStream;


import java.io.IOException;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PoiDetectEmptyRowUnitTest {
    
    private PoiHelper poiHelper = new PoiHelper();
    private static final String XLSX_EMPTY_FILE_PATH = "src/main/resources/empty_excel_file.xlsx";
    
    @Test
    public void givenXLSXFile_whenParsingExcelFile_thenDetectAllRowsEmpty() throws IOException {
        try (FileInputStream file = new FileInputStream(XLSX_EMPTY_FILE_PATH); Workbook workbook = new XSSFWorkbook(file)) {
            Sheet sheet = workbook.getSheetAt(0);

            for (int rowNum = 0; rowNum <= sheet.getLastRowNum(); rowNum++) {
                Row row = sheet.getRow(rowNum);
                assertTrue(poiHelper.isRowEmpty(row));
            }
        }
    }
    
}
