package com.baeldung.lossyconversion;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.jupiter.api.Test;

public class ConversionTechniquesUnitTest {

    @Test
    public void testPrimitiveConversion() {
        
        long longNum = 24;
        short shortNum = (short) longNum;
        assertEquals(24, shortNum);

        double doubleNum = 15.6;
        int integerNum = (int) doubleNum;
        assertEquals(15, integerNum);
        
        long largeLongNum = 32768;
        short minShortNum = (short) largeLongNum;
        assertEquals(-32768, minShortNum);
        
        long smallLongNum = -32769;
        short maxShortNum = (short) smallLongNum;
        assertEquals(32767, maxShortNum);
        
        long maxLong = Long.MAX_VALUE;
        int minInt = (int) maxLong;
        assertEquals(-1, minInt);
        
        long minLong = Long.MIN_VALUE;
        int maxInt = (int) minLong;
        assertEquals(0, maxInt);
    }

    @Test
    public void testWrapperToPrimitiveConversion() {

        Float floatNum = 17.564f;
        long longNum = floatNum.longValue();
        assertEquals(17, longNum);

        Double doubleNum = 15.9999;
        longNum = doubleNum.longValue();
        assertEquals(15, longNum);
    }

    @Test
    public void testWrapperToPrimitiveConversionUsingMathRound() {

        Double doubleNum = 15.9999;
        long longNum = Math.round(doubleNum);
        assertEquals(16, longNum);
    }
    
    @Test
    public void testWrapperConversion() {

        Double doubleNum = 10.3;
        double dbl = doubleNum.doubleValue(); //unboxing
        int intgr = (int) dbl; //downcasting
        Integer intNum = Integer.valueOf(intgr);
        assertEquals(Integer.valueOf(10), intNum);
    }
    
}
