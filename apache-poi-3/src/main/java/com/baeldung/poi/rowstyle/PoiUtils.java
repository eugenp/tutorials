package com.baeldung.poi.rowstyle;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

public class PoiUtils {

    private PoiUtils() {
    }

    private static void newCell(Row row, String value) {
        short cellNum = row.getLastCellNum();
        if (cellNum == -1)
            cellNum = 0;

        Cell cell = row.createCell(cellNum);
        cell.setCellValue(value);
    }

    public static Row newRow(Sheet sheet, String... rowValues) {
        Row row = sheet.createRow(sheet.getLastRowNum() + 1);

        for (String value : rowValues) {
            newCell(row, value);
        }

        return row;
    }

    public static CellStyle boldFontStyle(Workbook workbook) {
        Font boldFont = workbook.createFont();
        boldFont.setBold(true);

        CellStyle boldStyle = workbook.createCellStyle();
        boldStyle.setFont(boldFont);

        return boldStyle;
    }

    public static void write(Workbook workbook, Path path) throws IOException {
        try (FileOutputStream fileOut = new FileOutputStream(path.toFile())) {
            workbook.write(fileOut);
        }
    }
}
