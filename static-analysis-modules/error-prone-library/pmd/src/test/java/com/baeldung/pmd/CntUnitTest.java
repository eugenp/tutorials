package com.baeldung.pmd;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class CntUnitTest {

    private Cnt service;

    @Test
    public void whenSecondParamIsZeroShouldReturnIntMax(){
        service = new Cnt();
        int answer = service.d(100,0);
        assertEquals(Integer.MAX_VALUE, answer);
    }
}
