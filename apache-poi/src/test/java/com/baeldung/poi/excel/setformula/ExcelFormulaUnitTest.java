package com.baeldung.poi.excel.setformula;

import org.apache.poi.ss.util.CellReference;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Paths;

class ExcelFormulaUnitTest {
    private static String FILE_NAME = "com/bealdung/poi/excel/setformula/SetFormulaTest.xlsx";
    private String fileLocation;
    private ExcelFormula excelFormula;

    @BeforeEach
    public void setup() throws URISyntaxException {
        fileLocation = Paths.get(ClassLoader.getSystemResource(FILE_NAME).toURI()).toString();
        excelFormula = new ExcelFormula();
    }

    @Test
    void givenExcelData_whenSetFormula_thenSuccess() throws IOException {
        FileInputStream inputStream = new FileInputStream(new File(fileLocation));
        XSSFWorkbook wb = new XSSFWorkbook(inputStream);
        XSSFSheet sheet = wb.getSheetAt(0);
        double resultColumnA = 0;
        double resultColumnB = 0;
        for (int row = 0; row <= sheet.getLastRowNum(); row++) {
            resultColumnA += sheet.getRow(row).getCell(0).getNumericCellValue();
            resultColumnB += sheet.getRow(row).getCell(1).getNumericCellValue();
        }
        String colNameA = CellReference.convertNumToColString(0);
        String colNameB = CellReference.convertNumToColString(1);
        String startCellA = colNameA + 1;
        String stopCellA = colNameA + (sheet.getLastRowNum() + 1);
        String sumFormulaForColumnA = String.format("SUM(%s:%s)", startCellA, stopCellA);
        String startCellB = colNameB + 1;
        String stopCellB = colNameB + (sheet.getLastRowNum() + 1);
        String sumFormulaForColumnB = String.format("SUM(%s:%s)", startCellB, stopCellB);

        double resultValue = excelFormula.setFormula(fileLocation, wb, sumFormulaForColumnA + "-" + sumFormulaForColumnB);

        Assert.assertEquals(resultColumnA - resultColumnB, resultValue, 0d);
    }
}
