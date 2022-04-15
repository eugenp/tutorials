package com.bealdung.datescanner;

import static org.junit.Assert.*;

import org.junit.Assert;
import org.junit.Test;

public class DateScannerUnitTest {
    @Test
    public void whenDateIsString_thenResultIsDate(){
        Assert.assertEquals("2019-05-30", DateScanner.txtDate("Thursday 30 May 2019"));
    }
}