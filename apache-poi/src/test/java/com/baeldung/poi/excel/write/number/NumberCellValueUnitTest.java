package com.baeldung.poi.excel.write.number;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import com.baeldung.poi.excel.setnumberic.ExcelNumericFormat;

public class NumberCellValueUnitTest {

    @Test
    public void decimalDisplay_whenAddedInteger_thenNumericCellCreated() throws IOException {
        File file = new File("number_test.xlsx");
        try (Workbook outWorkbook = new XSSFWorkbook()) {
            Sheet sheet = outWorkbook.createSheet("Numeric Sheet");
            Row row = sheet.createRow(0);
            Cell cell = row.createCell(0);
            ExcelNumericFormat.applyNumbericFormat(outWorkbook, row, cell, 10.251, "0.00");
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
    public void desimalRoundedDisplay_whenAddedDuble_thenNumericCellCreated() throws IOException {
        File file = new File("number_test.xlsx");
        try (Workbook outWorkbook = new XSSFWorkbook()) {
            Sheet sheet = outWorkbook.createSheet("Numeric Sheet");
            Row row = sheet.createRow(0);
            Cell cell = row.createCell(0);
            ExcelNumericFormat.applyNumbericFormat(outWorkbook, row, cell, 10.251123, "#,##0.0000");
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
    public void decimalDisplayInXLS_whenAddedInteger_thenNumericCellCreated() throws IOException {
        File file = new File("number_test.xls");
        try (Workbook outWorkbook = new HSSFWorkbook()) {
            Sheet sheet = outWorkbook.createSheet("Numeric Sheet");
            Row row = sheet.createRow(0);
            Cell cell = row.createCell(0);
            ExcelNumericFormat.applyNumbericFormat(outWorkbook, row, cell, 10.251, "0.00");
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
    public void decimalValue_whenAddedInteger_thenNumericCellCreated() throws IOException {
        File file = new File("number_test.xlsx");
        try (Workbook outWorkbook = new XSSFWorkbook()) {
            Sheet sheet = outWorkbook.createSheet("Numeric Sheet");
            Row row = sheet.createRow(0);
            Cell cell = row.createCell(0);
            DecimalFormat df = new DecimalFormat("#,###.##");
            ExcelNumericFormat.applyNumbericFormat(outWorkbook, row, cell, Double.valueOf(df.format(10.251)), "#,###.##");

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