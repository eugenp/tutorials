package com.baeldung.poi.excel;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelUtility {
    private static final String ENDLINE = System.getProperty("line.separator");

    public static String readExcel(String filePath) throws IOException {
        File file = new File(filePath);
        FileInputStream inputStream = null;
        StringBuilder toReturn = new StringBuilder();
        try {
            inputStream = new FileInputStream(file);
            Workbook baeuldungWorkBook = new XSSFWorkbook(inputStream);
            for (Sheet sheet : baeuldungWorkBook) {
                toReturn.append("--------------------------------------------------------------------")
                    .append(ENDLINE);
                toReturn.append("Worksheet :")
                    .append(sheet.getSheetName())
                    .append(ENDLINE);
                toReturn.append("--------------------------------------------------------------------")
                    .append(ENDLINE);
                int firstRow = sheet.getFirstRowNum();
                int lastRow = sheet.getLastRowNum();
                for (int index = firstRow + 1; index <= lastRow; index++) {
                    Row row = sheet.getRow(index);
                    toReturn.append("|| ");
                    for (int cellIndex = row.getFirstCellNum(); cellIndex < row.getLastCellNum(); cellIndex++) {
                        Cell cell = row.getCell(cellIndex, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
                        printCellValue(cell, toReturn);
                    }
                    toReturn.append(" ||")
                        .append(ENDLINE);
                }
            }
            inputStream.close();
            baeuldungWorkBook.close();

        } catch (IOException e) {
            throw e;
        }
        return toReturn.toString();
    }

    public static void printCellValue(Cell cell, StringBuilder toReturn) {
        CellType cellType = cell.getCellType()
            .equals(CellType.FORMULA) ? cell.getCachedFormulaResultType() : cell.getCellType();
        if (cellType.equals(CellType.STRING)) {
            toReturn.append(cell.getStringCellValue())
                .append(" | ");
        }
        if (cellType.equals(CellType.NUMERIC)) {
            if (DateUtil.isCellDateFormatted(cell)) {
                toReturn.append(cell.getDateCellValue())
                    .append(" | ");
            } else {
                toReturn.append(cell.getNumericCellValue())
                    .append(" | ");
            }
        }
        if (cellType.equals(CellType.BOOLEAN)) {
            toReturn.append(cell.getBooleanCellValue())
                .append(" | ");
        }
    }
}