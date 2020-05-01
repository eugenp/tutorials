package com.baeldung.algorithms.printtriangles;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;

@RunWith(JUnitParamsRunner.class)
public class PrintTriangleExamplesUnitTest {

    private static Object[][] rightTriangles() {
        String expected0 = "";
        
        String expected2 = "*" + System.lineSeparator() 
                         + "**" + System.lineSeparator();
        
        String expected5 = "*" + System.lineSeparator() 
                         + "**" + System.lineSeparator()
                         + "***" + System.lineSeparator()
                         + "****" + System.lineSeparator()
                         + "*****" + System.lineSeparator();
        
        String expected7 = "*" + System.lineSeparator() 
                         + "**" + System.lineSeparator()
                         + "***" + System.lineSeparator()
                         + "****" + System.lineSeparator()
                         + "*****" + System.lineSeparator()
                         + "******" + System.lineSeparator()
                         + "*******" + System.lineSeparator();
        
        return new Object[][] {
            { 0, expected0 },
            { 2, expected2 },
            { 5, expected5 },
            { 7, expected7 }
        };
    }

    @Test
    @Parameters(method = "rightTriangles")
    public void whenPrintARightTriangleIsCalled_ThenTheCorrectStringIsReturned(int nrOfRows, String expected) {
        String actual = PrintTriangleExamples.printARightTriangle(nrOfRows);

        assertEquals(expected, actual);
    }
    
    private static Object[][] isoscelesTriangles() {
        String expected0 = ""; 
        
        String expected2 = " *" + System.lineSeparator() 
                         + "***" + System.lineSeparator();
        
        String expected5 = "    *" + System.lineSeparator() 
                         + "   ***" + System.lineSeparator()
                         + "  *****" + System.lineSeparator()
                         + " *******" + System.lineSeparator()
                         + "*********" + System.lineSeparator();
        
        String expected7 = "      *" + System.lineSeparator() 
                         + "     ***" + System.lineSeparator()
                         + "    *****" + System.lineSeparator()
                         + "   *******" + System.lineSeparator()
                         + "  *********" + System.lineSeparator()
                         + " ***********" + System.lineSeparator()
                         + "*************" + System.lineSeparator();
        
        return new Object[][] {
            { 0, expected0 },
            { 2, expected2 },
            { 5, expected5 },
            { 7, expected7 }
        };
    }

    @Test
    @Parameters(method = "isoscelesTriangles")
    public void whenPrintAnIsoscelesTriangleIsCalled_ThenTheCorrectStringIsReturned(int nrOfRows, String expected) {
        String actual = PrintTriangleExamples.printAnIsoscelesTriangle(nrOfRows);

        assertEquals(expected, actual);
    }

    @Test
    @Parameters(method = "isoscelesTriangles")
    public void whenPrintAnIsoscelesTriangleUsingStringUtilsIsCalled_ThenTheCorrectStringIsReturned(int nrOfRows, String expected) {
        String actual = PrintTriangleExamples.printAnIsoscelesTriangleUsingStringUtils(nrOfRows);

        assertEquals(expected, actual);
    }
    
    @Test
    @Parameters(method = "isoscelesTriangles")
    public void whenPrintAnIsoscelesTriangleUsingSubstringIsCalled_ThenTheCorrectStringIsReturned(int nrOfRows, String expected) {
        String actual = PrintTriangleExamples.printAnIsoscelesTriangleUsingSubstring(nrOfRows);

        assertEquals(expected, actual);
    }

}
