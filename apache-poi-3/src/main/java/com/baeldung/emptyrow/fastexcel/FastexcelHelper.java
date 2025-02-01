package com.baeldung.emptyrow.fastexcel;

import org.dhatim.fastexcel.reader.Cell;
import org.dhatim.fastexcel.reader.Row;

public class FastexcelHelper {
    public boolean isRowEmpty(Row row) {
        if (row == null) {
            return true;
        }
        for (Cell cell : row) {
            if (cell != null && !cell.getText().trim().isEmpty()) {
                return false;
            }
        }
        return true;
    }
}
