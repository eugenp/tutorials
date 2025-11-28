package com.baeldung.hssfworkbook;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

public class ExcelCreator {

    public static HSSFWorkbook createSampleWorkbook() {
        HSSFWorkbook workbook = new HSSFWorkbook();

        final String SHEET_NAME = "Employees";
        final String[] COLUMN_HEADERS = { "ID", "Name", "Department" };
        Object[][] data = { { 101, "John Doe", "Finance" }, { 102, "Jane Smith", "HR" }, { 103, "Michael Clark", "IT" } };

        Sheet sheet = workbook.createSheet(SHEET_NAME);

        HSSFFont font = workbook.createFont();
        font.setBold(true);
        HSSFCellStyle headerStyle = workbook.createCellStyle();
        headerStyle.setFont(font);

        Row header = sheet.createRow(0);
        for (int i = 0; i < COLUMN_HEADERS.length; i++) {
            Cell cell = header.createCell(i);
            cell.setCellValue(COLUMN_HEADERS[i]);
            cell.setCellStyle(headerStyle);
        }

        int rowNum = 1;
        for (Object[] rowData : data) {
            Row row = sheet.createRow(rowNum++);
            for (int i = 0; i < rowData.length; i++) {
                Cell cell = row.createCell(i);
                Object value = rowData[i];

                if (value instanceof Integer) {
                    cell.setCellValue(((Integer) value).doubleValue());
                } else if (value instanceof Double) {
                    cell.setCellValue((Double) value);
                } else if (value != null) {
                    cell.setCellValue(value.toString());
                }
            }
        }

        for (int i = 0; i < COLUMN_HEADERS.length; i++) {
            sheet.autoSizeColumn(i);
        }

        return workbook;
    }
}
