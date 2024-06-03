package com.baeldung.poi.columnnames;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.OLE2NotOfficeXmlFileException;
import org.apache.poi.poifs.filesystem.NotOLE2FileException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelUtils {

    public static Workbook openWorkbook(String filePath) throws IOException {
        try (InputStream fileInputStream = new FileInputStream(filePath)) {
            if (filePath.toLowerCase()
              .endsWith("xlsx")) {
                return new XSSFWorkbook(fileInputStream);
            } else if (filePath.toLowerCase()
              .endsWith("xls")) {
                return new HSSFWorkbook(fileInputStream);
            } else {
                throw new IllegalArgumentException("The specified file is not an Excel file");
            }
        } catch (OLE2NotOfficeXmlFileException | NotOLE2FileException e) {
            throw new IllegalArgumentException(
              "The file format is not supported. Ensure the file is a valid Excel file.", e);
        }
    }

    public static Sheet getSheet(Workbook workbook, String sheetName) {
        return workbook.getSheet(sheetName);
    }

    public static List<String> getColumnNames1(Sheet sheet) {
        List<String> columnNames = new ArrayList<>();
        Row headerRow = sheet.getRow(0);
        if (headerRow != null) {
            for (Cell cell : headerRow) {
                if (cell.getCellType() != CellType.BLANK && cell.getStringCellValue() != null
                  && !cell.getStringCellValue()
                  .trim()
                  .isEmpty()) {
                    columnNames.add(cell.getStringCellValue()
                      .trim());
                }
            }
        }
        return columnNames;
    }

    public static List<String> getColumnNames(Sheet sheet) {
        Row headerRow = sheet.getRow(0);
        if (headerRow == null) {
            return Collections.EMPTY_LIST;
        }
        return StreamSupport.stream(headerRow.spliterator(), false)
          .filter(cell -> cell.getCellType() != CellType.BLANK)
          .map(nonEmptyCell -> nonEmptyCell.getStringCellValue())
          .filter(cellValue -> cellValue != null && !cellValue.trim()
            .isEmpty())
          .map(String::trim)
          .collect(Collectors.toList());
    }

}
