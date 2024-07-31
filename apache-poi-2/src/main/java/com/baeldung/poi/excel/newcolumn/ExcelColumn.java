package com.baeldung.poi.excel.newcolumn;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


public class ExcelColumn {

    public void addColumn(Sheet sheet, CellType cellType) {
        for (Row currentRow : sheet) {
            currentRow.createCell(currentRow.getLastCellNum(), cellType);
        }
    }
}
