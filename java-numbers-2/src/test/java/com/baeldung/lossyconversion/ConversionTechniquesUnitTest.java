package com.baeldung.lossyconversion;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.jupiter.api.Test;

public class ConversionTechniquesUnitTest {

    @Test
    public void testPrimitiveConversion() {
        
        long longNum = 24;
        short shortNum = (short) longNum;
        
        assertEquals(shortNum, 24);

        double doubleNum = 15.6;
        int integerNum = (int) doubleNum;

        assertEquals(integerNum, 15);
    }

    @Test
    public void testWrapperToPrimitiveConversion() {

        Float floatNum = 17.564f;
        long longNum = floatNum.longValue();

        assertEquals(longNum, 17l);

        Double doubleNum = 15.9999;
        int intNum = doubleNum.intValue();

        assertEquals(intNum, 15);

        longNum = Math.round(doubleNum);

        assertEquals(longNum, 16);
    }

    @Test
    public void testWrapperToPrimitiveConversionUsingMathRound() {

        Double doubleNum = 15.9999;
        long longNum = Math.round(doubleNum);

        assertEquals(longNum, 16);
    }
    
    @Test
    public void testWrapperConversion() {

        Double doubleNum = 10.3;
        double dbl = doubleNum.doubleValue(); //unboxing
        int intgr = (int) dbl; //downcasting
        Integer intNum = Integer.valueOf(intgr);

        assertTrue(intNum == 10);
    }
    
}
