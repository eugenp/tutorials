package com.baeldung.poi.excel.dateformat;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelDateFormat {

    public final static String CUSTOM_DATE_FORMAT = "m/d/yy h:mm";

    /**
     * Set default date format to a cell
     * @param dateCell cell to set date format
     * @param wb workbook to create date format cell style
     */
    public void setDefaultDateFormat(XSSFCell dateCell, XSSFWorkbook wb) {
        CellStyle cellStyle = wb.createCellStyle();
        cellStyle.setDataFormat((short) 14);
        dateCell.setCellStyle(cellStyle);
    }

    /**
     * Set custom date format to a cell
     * @param dateCell cell to set date format
     * @param wb workbook to create date format cell style
     */
    public void setCustomDateFormat(XSSFCell dateCell, XSSFWorkbook wb) {
        CellStyle cellStyle = wb.createCellStyle();
        CreationHelper createHelper = wb.getCreationHelper();
        short format = createHelper.createDataFormat().getFormat(CUSTOM_DATE_FORMAT);
        cellStyle.setDataFormat(format);
        dateCell.setCellStyle(cellStyle);
    }
}
