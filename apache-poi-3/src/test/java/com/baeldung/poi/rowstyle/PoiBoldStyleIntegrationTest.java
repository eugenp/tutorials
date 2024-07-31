package com.baeldung.poi.rowstyle;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.jupiter.api.Test;

class PoiBoldStyleIntegrationTest {

    private void writeSampleSheet(Path destination, Workbook workbook) throws IOException {
        Sheet sheet = workbook.createSheet();
        CellStyle boldStyle = PoiUtils.boldFontStyle(workbook);

        Row header = PoiUtils.newRow(sheet, "Name", "Value", "Details");
        header.setRowStyle(boldStyle);

        PoiUtils.newRow(sheet, "Albert", "A", "First");
        PoiUtils.newRow(sheet, "Jane", "B", "Second");
        PoiUtils.newRow(sheet, "Zack", "C", "Third");

        PoiUtils.write(workbook, destination);
    }

    private void assertRowStyleAppliedAndDefaultCellStylesDontMatch(Path sheetFile) throws IOException, InvalidFormatException {
        try (Workbook workbook = new XSSFWorkbook(sheetFile.toFile())) {
            Sheet sheet = workbook.getSheetAt(0);
            Row row0 = sheet.getRow(0);

            XSSFCellStyle rowStyle = (XSSFCellStyle) row0.getRowStyle();
            assertTrue(rowStyle.getFont()
                .getBold());

            row0.forEach(cell -> {
                XSSFCellStyle style = (XSSFCellStyle) cell.getCellStyle();
                assertNotEquals(rowStyle, style);
            });

            Row row1 = sheet.getRow(1);
            XSSFCellStyle row1Style = (XSSFCellStyle) row1.getRowStyle();
            assertNull(row1Style);

            Files.delete(sheetFile);
        }
    }

    @Test
    void givenXssfWorkbook_whenSetRowStyle1stRow_thenOnly1stRowStyled() throws IOException, InvalidFormatException {
        Path sheetFile = Files.createTempFile("xssf-row-style", ".xlsx");

        try (Workbook workbook = new XSSFWorkbook()) {
            writeSampleSheet(sheetFile, workbook);
        }

        assertRowStyleAppliedAndDefaultCellStylesDontMatch(sheetFile);
    }

    @Test
    void givenSxssfWorkbook_whenSetRowStyle_thenOnly1stRowStyled() throws IOException, InvalidFormatException {
        Path sheetFile = Files.createTempFile("sxssf-row-style", ".xlsx");

        try (Workbook workbook = new SXSSFWorkbook()) {
            writeSampleSheet(sheetFile, workbook);
        }

        assertRowStyleAppliedAndDefaultCellStylesDontMatch(sheetFile);
    }

    @Test
    void givenHssfWorkbook_whenSetRowStyle_thenOnly1stRowStyled() throws IOException {
        Path sheetFile = Files.createTempFile("hssf-row-style", ".xls");

        try (Workbook workbook = new HSSFWorkbook()) {
            writeSampleSheet(sheetFile, workbook);
        }

        try (Workbook workbook = new HSSFWorkbook(Files.newInputStream(sheetFile))) {
            Sheet sheet = workbook.getSheetAt(0);
            Row row0 = sheet.getRow(0);

            HSSFCellStyle rowStyle = (HSSFCellStyle) row0.getRowStyle();
            assertTrue(rowStyle.getFont(workbook)
                .getBold());

            row0.forEach(cell -> {
                HSSFCellStyle style = (HSSFCellStyle) cell.getCellStyle();
                assertNotEquals(rowStyle, style);
            });

            Row row1 = sheet.getRow(1);
            HSSFCellStyle row1Style = (HSSFCellStyle) row1.getRowStyle();
            assertNull(row1Style);

            Files.delete(sheetFile);
        }
    }

    @Test
    void givenXssfWorkbook_whenSetCellStyleForEachRow_thenAllCellsContainStyle() throws IOException, InvalidFormatException {
        Path sheetFile = Files.createTempFile("xssf-cell-style", ".xlsx");

        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet();
            CellStyle boldStyle = PoiUtils.boldFontStyle(workbook);

            Row header = PoiUtils.newRow(sheet, "Name", "Value", "Details");
            header.forEach(cell -> cell.setCellStyle(boldStyle));

            PoiUtils.newRow(sheet, "Albert", "A", "First");
            PoiUtils.newRow(sheet, "Jane", "B", "Second");
            PoiUtils.newRow(sheet, "Zack", "C", "Third");

            PoiUtils.write(workbook, sheetFile);
        }

        try (Workbook workbook = new XSSFWorkbook(sheetFile.toFile())) {
            Sheet sheet = workbook.getSheetAt(0);
            Row row0 = sheet.getRow(0);

            XSSFCellStyle rowStyle = (XSSFCellStyle) row0.getRowStyle();
            assertNull(rowStyle);

            row0.forEach(cell -> {
                XSSFCellStyle style = (XSSFCellStyle) cell.getCellStyle();
                assertTrue(style.getFont()
                    .getBold());
            });

            Row row1 = sheet.getRow(1);
            rowStyle = (XSSFCellStyle) row1.getRowStyle();
            assertNull(rowStyle);

            Files.delete(sheetFile);
        }
    }
}
