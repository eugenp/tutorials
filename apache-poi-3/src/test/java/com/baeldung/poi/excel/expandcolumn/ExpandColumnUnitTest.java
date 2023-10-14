package com.baeldung.poi.excel.expandcolumn;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ExpandColumnUnitTest {

    private Workbook workbook;
    private Sheet sheet;

    @BeforeEach
    void prepareSpreadsheet() {
        workbook = new XSSFWorkbook();
        sheet = workbook.createSheet();

        Row headerRow = sheet.createRow(0);
        Cell headerCell1 = headerRow.createCell(0);
        headerCell1.setCellValue("Full Name");
        Cell headerCell2 = headerRow.createCell(1);
        headerCell2.setCellValue("Abbreviation");

        Row dataRow = sheet.createRow(1);
        Cell dataCell1 = dataRow.createCell(0);
        dataCell1.setCellValue("Java Virtual Machine");
        Cell dataCell2 = dataRow.createCell(1);
        dataCell2.setCellValue("JVM");

        dataRow = sheet.createRow(2);
        dataCell1 = dataRow.createCell(0);
        dataCell1.setCellValue("Java Runtime Environment");
        dataCell2 = dataRow.createCell(1);
        dataCell2.setCellValue("JRE");
    }

    @Test
    void whenSetColumnWidth_thenColumnSetToTheSpecifiedWidth() {

        Row row = sheet.getRow(2);
        String cellValue = row.getCell(0).getStringCellValue();
        int targetWidth = cellValue.length() * 256;

        sheet.setColumnWidth(0, targetWidth);

        assertEquals(targetWidth, sheet.getColumnWidth(0));
    }

    @Test
    void whenAutoSizeColumn_thenColumnExpands() {

        int originalWidth = sheet.getColumnWidth(0);

        sheet.autoSizeColumn(0);

        assertThat(sheet.getColumnWidth(0)).isGreaterThan(originalWidth);
    }

    @AfterEach
    void cleanup() throws IOException {
        workbook.close();
    }

}