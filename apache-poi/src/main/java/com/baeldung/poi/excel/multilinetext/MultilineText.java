package com.baeldung.poi.excel.multilinetext;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;

public class MultilineText {
    public void formatMultilineText(Cell cell, int cellNumber) {
        cell.getRow()
            .setHeightInPoints(cell.getSheet()
                .getDefaultRowHeightInPoints() * 2);
        CellStyle cellStyle = cell.getSheet()
            .getWorkbook()
            .createCellStyle();
        cellStyle.setWrapText(true);
        cell.setCellStyle(cellStyle);
    }
}
