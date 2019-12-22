package com.baeldung.poi.excel;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Paths;

import org.junit.Before;
import org.junit.Test;

public class ReadCellValueNotFormulaUnitTest {

    private ReadCellValueNotFormulaHelper readCellValueNotFormulaHelper;
    private String fileLocation;
    private static final String FILE_NAME = "test.xlsx";

    @Before
    public void setup() throws URISyntaxException { 
        fileLocation = Paths.get(ClassLoader.getSystemResource(FILE_NAME).toURI()).toString();
        readCellValueNotFormulaHelper = new ReadCellValueNotFormulaHelper();
    }

    @Test
    public void testCachedValueMethod() throws IOException {
        final double expectedResult = 7.0;
        final Object cellValue = readCellValueNotFormulaHelper.getCellValueByFetchingLastCachedValue(fileLocation, "C2");

        assertEquals(expectedResult, cellValue);
    }

    @Test
    public void testFormulaEvaluationMethod() throws IOException {
        final double expectedResult = 7.0;
        final Object cellValue = readCellValueNotFormulaHelper.getCellValueByEvaluatingFormula(fileLocation, "C2");

        assertEquals(expectedResult, cellValue);
    }
}
