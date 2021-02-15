package com.baeldung.poi.excel.insert;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.IOException;

public class InsertRowHelper {
    public Workbook insertRowBetweenRows(String fileLocation, int startRow, int rowNumber) throws IOException {
        Workbook workbook = new XSSFWorkbook(fileLocation);
        Sheet sheet = workbook.getSheetAt(0);
        int lastRow = sheet.getLastRowNum();
        if (lastRow < startRow) {
            sheet.createRow(startRow);
        }
        sheet.shiftRows(startRow, lastRow, rowNumber, true, true);
        sheet.createRow(startRow);
        return workbook;
    }
}
