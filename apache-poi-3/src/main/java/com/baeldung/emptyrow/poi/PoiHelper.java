package com.baeldung.emptyrow.poi;

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

public class PoiHelper {

    public Workbook openWorkbook(String filePath) throws IOException {
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
    
    public boolean isRowEmpty(Row row) {
        if (row == null) {
            return true;
        }
        for (int cellNum = row.getFirstCellNum(); cellNum < row.getLastCellNum(); cellNum++) {
            Cell cell = row.getCell(cellNum);
            if (cell != null && cell.getCellType() != CellType.BLANK) {
                return false;
            }
        }
        return true;
    }
}
