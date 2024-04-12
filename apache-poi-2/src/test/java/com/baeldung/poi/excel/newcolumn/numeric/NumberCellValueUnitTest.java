package com.baeldung.poi.excel.newcolumn.numeric;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;

public class NumberCellValueUnitTest {

    @Test
    public void decimalDisplay_whenAddedDouble_thenNumericCellCreated() throws IOException {
        File file = new File("number_test.xlsx");
        try (Workbook outWorkbook = new XSSFWorkbook()) {
            Sheet sheet = outWorkbook.createSheet("Numeric Sheet");
            Row row = sheet.createRow(0);
            Cell cell = row.createCell(0);
            ExcelNumericFormat.applyNumericFormat(outWorkbook, row, cell, 10.251, "0.00");
            FileOutputStream fileOut = new FileOutputStream(file);
            outWorkbook.write(fileOut);
            fileOut.close();
        }
        try (Workbook inWorkbook = new XSSFWorkbook("number_test.xlsx")) {
            Sheet sheet = inWorkbook.cloneSheet(0);
            Row row = sheet.getRow(0);
            Assertions.assertEquals(10.251, row.getCell(0)
                .getNumericCellValue());
            file.delete();
        }
    }

    @Test
    public void decimalRoundedDisplay_whenAddedDouble_thenNumericCellCreated() throws IOException {
        File file = new File("number_test.xlsx");
        try (Workbook outWorkbook = new XSSFWorkbook()) {
            Sheet sheet = outWorkbook.createSheet("Numeric Sheet");
            Row row = sheet.createRow(0);
            Cell cell = row.createCell(0);
            ExcelNumericFormat.applyNumericFormat(outWorkbook, row, cell, 10.251123, "#,##0.0000");
            FileOutputStream fileOut = new FileOutputStream(file);
            outWorkbook.write(fileOut);
            fileOut.close();
        }
        try (Workbook inWorkbook = new XSSFWorkbook("number_test.xlsx")) {
            Sheet sheet = inWorkbook.cloneSheet(0);
            Row row = sheet.getRow(0);
            Assertions.assertEquals(10.251123, row.getCell(0)
                .getNumericCellValue());
            file.delete();
        }
    }

    @Test
    public void decimalDisplayInXLS_whenAddedDouble_thenNumericCellCreated() throws IOException {
        File file = new File("number_test.xls");
        try (Workbook outWorkbook = new HSSFWorkbook()) {
            Sheet sheet = outWorkbook.createSheet("Numeric Sheet");
            Row row = sheet.createRow(0);
            Cell cell = row.createCell(0);
            ExcelNumericFormat.applyNumericFormat(outWorkbook, row, cell, 10.251, "0.00");
            FileOutputStream fileOut = new FileOutputStream(file);
            outWorkbook.write(fileOut);
            fileOut.close();
        }
        try (Workbook inWorkbook = new HSSFWorkbook(new FileInputStream(file))) {
            Sheet sheet = inWorkbook.cloneSheet(0);
            Row row = sheet.getRow(0);
            Assertions.assertEquals(10.251, row.getCell(0)
                .getNumericCellValue());
            file.delete();
        }
    }

    @Test
    public void decimalValue_whenAddedDouble_thenNumericCellCreated() throws IOException {
        File file = new File("number_test.xlsx");
        try (Workbook outWorkbook = new XSSFWorkbook()) {
            Sheet sheet = outWorkbook.createSheet("Numeric Sheet");
            Row row = sheet.createRow(0);
            Cell cell = row.createCell(0);
            DecimalFormat df = new DecimalFormat("#,###.##");
            ExcelNumericFormat.applyNumericFormat(outWorkbook, row, cell, Double.valueOf(df.format(10.251)), "#,###.##");

            FileOutputStream fileOut = new FileOutputStream(file);
            outWorkbook.write(fileOut);
            fileOut.close();
        }
        try (Workbook inWorkbook = new XSSFWorkbook("number_test.xlsx")) {
            Sheet sheet = inWorkbook.cloneSheet(0);
            Row row = sheet.getRow(0);
            Assertions.assertEquals(10.25, row.getCell(0)
                .getNumericCellValue());
            file.delete();
        }
    }
}