package com.baeldung.poi.excel.merge;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Paths;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ExcelCellMergerUnitTest {

    private static final String FILE_NAME = "ExcelCellFormatterTest2.xlsx";
    private static final String FILE_NAME_2 = "MergedAlignCell.xlsx";

    private String fileLocation;
    private String fileLocation2;

    @BeforeEach
    void setup() throws IOException, URISyntaxException {
        fileLocation = Paths.get(ClassLoader.getSystemResource(FILE_NAME)
            .toURI())
            .toString();

        fileLocation2 = Paths.get(ClassLoader.getSystemResource(FILE_NAME_2)
            .toURI())
            .toString();
    }

    @Test
    void givenCellIndex_whenAddMergeRegion_thenMergeRegionCreated() throws IOException {
        try (Workbook workbook = new XSSFWorkbook(fileLocation)) {
            Sheet sheet = workbook.getSheetAt(0);
            assertEquals(0, sheet.getNumMergedRegions());
            int firstRow = 0;
            int lastRow = 0;
            int firstCol = 0;
            int lastCol = 2;
            sheet.addMergedRegion(new CellRangeAddress(firstRow, lastRow, firstCol, lastCol));
            assertEquals(1, sheet.getNumMergedRegions());
        }
    }

    @Test
    void givenCellRefString_whenAddMergeRegion_thenMergeRegionCreated() throws IOException {
        try (Workbook workbook = new XSSFWorkbook(fileLocation)) {
            Sheet sheet = workbook.getSheetAt(0);
            assertEquals(0, sheet.getNumMergedRegions());
            sheet.addMergedRegion(CellRangeAddress.valueOf("A1:C1"));
            assertEquals(1, sheet.getNumMergedRegions());
        }
    }

    @Test
    void givenCellIndex_whenAddMergeRegionWithCenterAlignment_thenMergeRegionWithCenterCreated() throws IOException {
        try (Workbook workbook = new XSSFWorkbook(fileLocation2)) {
            Sheet sheet = workbook.getSheetAt(0);

            assertEquals(0, sheet.getNumMergedRegions());
            int firstRow = 0;
            int lastRow = 0;
            int firstCol = 0;
            int lastCol = 2;
            sheet.addMergedRegion(new CellRangeAddress(firstRow, lastRow, firstCol, lastCol));

            // Create a cell style with center alignment
            CellStyle cellStyle = workbook.createCellStyle();
            cellStyle.setAlignment(HorizontalAlignment.CENTER);
            cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);

            // Apply style to the merged cell
            Row row = sheet.getRow(firstRow);
            Cell cell = row.getCell(firstCol);
            cell.setCellStyle(cellStyle);

            // Verify merge region
            assertEquals(1, sheet.getNumMergedRegions());

            // Verify cell style (alignment)
            Cell readCell = sheet.getRow(firstRow)
                .getCell(firstCol);
            assertNotNull(readCell);
            CellStyle readCellStyle = readCell.getCellStyle();
            assertEquals(HorizontalAlignment.CENTER, readCellStyle.getAlignment());
            assertEquals(VerticalAlignment.CENTER, readCellStyle.getVerticalAlignment());
        }
    }

}