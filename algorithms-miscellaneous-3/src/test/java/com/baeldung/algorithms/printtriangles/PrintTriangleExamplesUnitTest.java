package com.baeldung.algorithms.printtriangles;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;

@RunWith(JUnitParamsRunner.class)
public class PrintTriangleExamplesUnitTest {
    
    private static Object[][] rightAngledTriangles() {
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
    @Parameters(method = "rightAngledTriangles")
    public void whenPrintARightAngledTriangleIsCalled_ThenTheCorrectStringIsReturned(int nrOfRows, String expected) {
        String actual = PrintTriangleExamples.printARightAngledTriangle(nrOfRows);

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
    public void whenPrintAnIsoscelesTriangleUsingSubstringIsCalled_ThenTheCorrectStringIsReturned(int nrOfRows, String expected) {
        String actual = PrintTriangleExamples.printAnIsoscelesTriangleUsingSubstring(nrOfRows);

        assertEquals(expected, actual);
    }

}
