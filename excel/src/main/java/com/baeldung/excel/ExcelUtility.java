package com.baeldung.excel;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class ExcelUtility {
    private static final String PATH = "\\ENTER\\PATH\\HERE";

    public static void main(String[] args) {
        readExcel(PATH);
    }

    public static void readExcel(String filePath) {
        File file = new File(filePath);
        FileInputStream inputStream = null;
        try {
            inputStream = new FileInputStream(file);
            Workbook baeuldungWorkBook = new XSSFWorkbook(inputStream);
            for (Sheet sheet : baeuldungWorkBook) {
                System.out.println("--------------------------------------------------------------------");
                System.out.println("Worksheet :" + sheet.getSheetName());
                System.out.println("--------------------------------------------------------------------");

                int firstRow = sheet.getFirstRowNum();
                int lastRow = sheet.getLastRowNum();
                for (int index = firstRow + 1; index <= lastRow; index++) {
                    Row row = sheet.getRow(index);
                    System.out.print("|| ");
                    for (int cellIndex = row.getFirstCellNum(); cellIndex < row.getLastCellNum(); cellIndex++) {
                        Cell cell = row.getCell(cellIndex, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
                        printCellValue(cell);
                    }
                    System.out.println(" ||");
                }
            }
            inputStream.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void printCellValue(Cell cell) {
        CellType cellType = cell.getCellType().equals(CellType.FORMULA) ? cell.getCachedFormulaResultType() : cell.getCellType();
        switch (cellType) {
            case STRING:
                System.out.print(cell.getStringCellValue() + " | ");
            case NUMERIC:
                if (DateUtil.isCellDateFormatted(cell)) {
                    System.out.print(cell.getDateCellValue() + " | ");
                } else {
                    System.out.print(cell.getNumericCellValue() + " | ");
                }
            case BOOLEAN:
                System.out.print(cell.getBooleanCellValue() + " | ");
        }

    }
}
