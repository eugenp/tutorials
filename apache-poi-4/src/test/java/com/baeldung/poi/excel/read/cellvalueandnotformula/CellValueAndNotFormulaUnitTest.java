package com.baeldung.poi.excel.read.cellvalueandnotformula;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Paths;

import org.junit.Before;
import org.junit.Test;

public class CellValueAndNotFormulaUnitTest {

    private CellValueAndNotFormulaHelper readCellValueAndNotFormulaHelper;
    private String fileLocation;
    private static final String FILE_NAME = "test.xlsx";

    @Before
    public void setup() throws URISyntaxException {
        fileLocation = Paths.get(ClassLoader.getSystemResource(FILE_NAME).toURI()).toString();
        readCellValueAndNotFormulaHelper = new CellValueAndNotFormulaHelper();
    }

    @Test
    public void givenExcelCell_whenReadCellValueByLastCachedValue_thenProduceCorrectResult() throws IOException {
        final double expectedResult = 7.0;
        final Object cellValue = readCellValueAndNotFormulaHelper.getCellValueByFetchingLastCachedValue(fileLocation, "C2");

        assertEquals(expectedResult, cellValue);
    }

    @Test
    public void givenExcelCell_whenReadCellValueByEvaluatingFormula_thenProduceCorrectResult() throws IOException {
        final double expectedResult = 7.0;
        final Object cellValue = readCellValueAndNotFormulaHelper.getCellValueByEvaluatingFormula(fileLocation, "C2");

        assertEquals(expectedResult, cellValue);
    }
}
