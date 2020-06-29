package com.bealdung.poi.excelformula;

import org.apache.poi.ss.usermodel.CellValue;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFFormulaEvaluator;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Component
public class ExcelFormula {
    XSSFWorkbook excel;
    public XSSFSheet inserData(List<Integer> dataList) {
        excel = new XSSFWorkbook();
        XSSFSheet sheet = excel.createSheet();
        int rowNum =0 ;
        for (Integer data : dataList){
            sheet.createRow(rowNum++).createCell(0).setCellValue(data);
        }
        return sheet;
    }
    public double setFormula(String formula) throws IOException {
        XSSFSheet sheet = excel.getSheetAt(0);
        int lastCellNum = sheet.getRow(0).getLastCellNum();
        XSSFCell formulaCell = sheet.getRow(0).createCell(lastCellNum + 1);
        formulaCell.setCellFormula(formula);
        XSSFFormulaEvaluator formulaEvaluator = excel.getCreationHelper().createFormulaEvaluator();
        CellValue evaluate = formulaEvaluator.evaluate(formulaCell);
        if(excel != null)
            excel.close();
        return evaluate.getNumberValue();
    }
}
