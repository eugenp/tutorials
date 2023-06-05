package com.baeldung.poi.excel.dateformat;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static com.baeldung.poi.excel.dateformat.ExcelDateFormat.CUSTOM_DATE_FORMAT;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class DateFormatUnitTest {

    private ExcelDateFormat excelDateFormat;

    private XSSFWorkbook wb;

    private Date date;


    @BeforeEach
    public void setup() {
        excelDateFormat = new ExcelDateFormat();
        wb = new XSSFWorkbook();
        wb.createSheet();
        date = new Date();
    }

    @Test
    void givenExcelDate_whenSetDefaultDate_thenSuccess() {
        // given Excel Sheet
        XSSFSheet sheet = wb.getSheetAt(0);
        XSSFCell dateCell = sheet.createRow(0).createCell(0);

        // when set default date format
        excelDateFormat.setDefaultDateFormat(dateCell, wb);
        dateCell.setCellValue(date);

        // then success
        assertEquals(date, dateCell.getDateCellValue());
    }

    @Test
    void givenExcelDate_whenSetCustomDate_thenSuccess() {
        // given Excel Sheet
        XSSFSheet sheet = wb.getSheetAt(0);
        XSSFCell dateCellCustom = sheet.createRow(0).createCell(0);

        // when set custom date format
        excelDateFormat.setCustomDateFormat(dateCellCustom, wb);
        dateCellCustom.setCellValue(date);

        // then success
        assertEquals(CUSTOM_DATE_FORMAT, dateCellCustom.getCellStyle().getDataFormatString());
        assertEquals(date, dateCellCustom.getDateCellValue());
    }

    @Test
    void givenExcelDefaultDateFormat_whenGetDefaultDateFormat_thenSuccess() {
        // given Excel cell style with default date format
        CellStyle cellStyle = wb.createCellStyle();

        // when get default date format
        cellStyle.setDataFormat((short) 14);
        // then success
        assertEquals("m/d/yy", cellStyle.getDataFormatString());

        cellStyle.setDataFormat((short) 15);
        assertEquals("d-mmm-yy", cellStyle.getDataFormatString());

        cellStyle.setDataFormat((short) 16);
        assertEquals("d-mmm", cellStyle.getDataFormatString());

        cellStyle.setDataFormat((short) 17);
        assertEquals("mmm-yy", cellStyle.getDataFormatString());

        cellStyle.setDataFormat((short) 18);
        assertEquals("h:mm AM/PM", cellStyle.getDataFormatString());

        cellStyle.setDataFormat((short) 19);
        assertEquals("h:mm:ss AM/PM", cellStyle.getDataFormatString());

        cellStyle.setDataFormat((short) 20);
        assertEquals("h:mm", cellStyle.getDataFormatString());

        cellStyle.setDataFormat((short) 21);
        assertEquals("h:mm:ss", cellStyle.getDataFormatString());

        cellStyle.setDataFormat((short) 22);
        assertEquals("m/d/yy h:mm", cellStyle.getDataFormatString());
    }
}
