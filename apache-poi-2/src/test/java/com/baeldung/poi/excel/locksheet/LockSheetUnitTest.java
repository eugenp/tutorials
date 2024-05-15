package com.baeldung.poi.excel.locksheet;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.jupiter.api.*;

class LockSheetUnitTest {

    private LockSheet lockSheet;
    private Workbook workbook;
    private Sheet sheet;

    @BeforeEach
    void setup() {
        workbook = new XSSFWorkbook();
        sheet = workbook.createSheet();
        Row row = sheet.createRow(0);
        row.createCell(0).setCellValue("row 1 col 1");
        row.createCell(1).setCellValue("row 1 col 2");
        row = sheet.createRow(1);
        row.createCell(0).setCellValue("row 2 col 1");
        row.createCell(1).setCellValue("row 2 col 2");
        lockSheet = new LockSheet();
    }

    @AfterEach
    void cleanup() throws IOException {
        workbook.close();
    }

    @Test
    void whenLockFirstRow_thenFirstRowIsLocked() {
        lockSheet.lockFirstRow(sheet);
        assertEquals(sheet.getPaneInformation().getHorizontalSplitPosition(), 1);
    }

    @Test
    void whenLockTwoRows_thenTwoRowsAreLocked() {
        lockSheet.lockTwoRows(sheet);
        assertEquals(sheet.getPaneInformation().getHorizontalSplitPosition(), 2);
    }

    @Test
    void whenLockFirstColumn_thenFirstColumnIsLocked() {
        lockSheet.lockFirstColumn(sheet);
        assertEquals(sheet.getPaneInformation().getVerticalSplitPosition(), 1);
    }

}