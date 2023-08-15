package com.baeldung.poi.excel.locksheet;

import org.apache.poi.ss.usermodel.*;

public class LockSheet {

    public void lockFirstRow(Sheet sheet) {
        sheet.createFreezePane(0, 1);
    }

    public void lockTwoRows(Sheet sheet) {
        sheet.createFreezePane(0, 2);
    }

    public void lockFirstColumn(Sheet sheet) {
        sheet.createFreezePane(1, 0);
    }

}