package com.baeldung.poi.excel.multilinetext;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;

public class ExcelMultilineText {

	public void FormatMultilineText(Row row, int cellNumber) {
		row.setHeightInPoints(row.getSheet().getDefaultRowHeightInPoints() * 2);
		CellStyle cellStyle = row.getSheet().getWorkbook().createCellStyle();
		cellStyle.setWrapText(true);
		row.getCell(cellNumber).setCellStyle(cellStyle);
	}

}
