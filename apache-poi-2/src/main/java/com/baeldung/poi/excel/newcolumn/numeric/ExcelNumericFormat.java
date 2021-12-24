package com.baeldung.poi.excel.newcolumn.numeric;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;

public class ExcelNumericFormat {

    public static void applyNumericFormat(Workbook outWorkbook, Row row, Cell cell, Double value, String styleFormat) {
        CellStyle style = outWorkbook.createCellStyle();
        DataFormat format = outWorkbook.createDataFormat();
        style.setDataFormat(format.getFormat(styleFormat));
        cell.setCellValue(value);
        cell.setCellStyle(style);
    }

}
