package com.baeldung.poi.excel.merge;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Paths;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Before;
import org.junit.Test;

public class ExcelCellMergerUnitTest {
    private static final String FILE_NAME = "ExcelCellFormatterTest.xlsx";
    private String fileLocation;

    @Before
    public void setup() throws IOException, URISyntaxException {
            fileLocation = Paths.get(ClassLoader.getSystemResource(FILE_NAME).toURI()).toString();
    }

    @Test
    public void givenCellIndex_whenAddMergeRegion_thenMergeRegionCreated() throws IOException {
        Workbook workbook = new XSSFWorkbook(fileLocation);
        Sheet sheet = workbook.getSheetAt(0);

        assertEquals(0, sheet.getNumMergedRegions());
        int firstRow = 0; 
        int lastRow = 0; 
        int firstCol = 0; 
        int lastCol = 2;
        sheet.addMergedRegion(new CellRangeAddress(firstRow, lastRow, firstCol, lastCol));
        assertEquals(1, sheet.getNumMergedRegions());

        workbook.close();
    }

    @Test
    public void givenCellRefString_whenAddMergeRegion_thenMergeRegionCreated() throws IOException {
        Workbook workbook = new XSSFWorkbook(fileLocation);
        Sheet sheet = workbook.getSheetAt(0);

        assertEquals(0, sheet.getNumMergedRegions());        
        sheet.addMergedRegion(CellRangeAddress.valueOf("A1:C1"));
        assertEquals(1, sheet.getNumMergedRegions());

        workbook.close();
    }

}