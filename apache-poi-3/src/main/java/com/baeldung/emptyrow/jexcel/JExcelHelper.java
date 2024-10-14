package com.baeldung.emptyrow.jexcel;

import jxl.Cell;

public class JExcelHelper {

    public boolean isRowEmpty(Cell[] row) {
        if (row == null) {
            return true;
        }
        for (Cell cell : row) {
            if (cell != null && !cell.getContents().trim().isEmpty()) {
                return false;
            }
        }
        return true;
    }
}