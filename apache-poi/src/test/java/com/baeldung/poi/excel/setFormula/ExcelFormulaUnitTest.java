package com.baeldung.poi.excel.setFormula;

import org.apache.poi.ss.util.CellReference;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

class ExcelFormulaUnitTest {
    @Test
    void givenExcelData_whenSetAndEvaluateFormula() throws IOException {
        ExcelFormula excelFormula = new ExcelFormula();
        List<Integer> data = Arrays.asList(2, 5, 10, 15, 7, 9);
        XSSFSheet sheet = excelFormula.inserData(data);
        int result = 0;
        for (int row = 0; row <= sheet.getLastRowNum(); row++) {
            result += sheet.getRow(row).getCell(0).getNumericCellValue();
        }
        String colName = CellReference.convertNumToColString(0);
        String startCell = colName + 1;
        String stopCell = colName + (sheet.getLastRowNum() + 1);
        String sumFormula = String.format("SUM(%s:%s)", startCell, stopCell);
        int resultValue = (int) excelFormula.setFormula(sumFormula);
        Assert.assertEquals("The results are the same!", resultValue , result);
    }
}
