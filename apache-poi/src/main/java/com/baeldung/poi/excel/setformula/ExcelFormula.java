package com.baeldung.poi.excel.setformula;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFFormulaEvaluator;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class ExcelFormula {
    public double setFormula(String fileLocation, XSSFWorkbook wb, String formula) throws IOException {
        XSSFSheet sheet = wb.getSheetAt(0);
        int lastCellNum = sheet.getRow(0).getLastCellNum();
        XSSFCell formulaCell = sheet.getRow(0).createCell(lastCellNum);
        formulaCell.setCellFormula(formula);
        XSSFFormulaEvaluator formulaEvaluator = wb.getCreationHelper().createFormulaEvaluator();
        formulaEvaluator.evaluateFormulaCell(formulaCell);
        FileOutputStream fileOut = new FileOutputStream(new File(fileLocation));
        wb.write(fileOut);
        wb.close();
        fileOut.close();
        return formulaCell.getNumericCellValue();
    }
}
