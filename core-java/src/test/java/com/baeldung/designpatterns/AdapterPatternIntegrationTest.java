package com.baeldung.designpatterns;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.baeldung.designpatterns.adapter.AstonMartin;
import com.baeldung.designpatterns.adapter.BugattiVeyron;
import com.baeldung.designpatterns.adapter.LuxuryCarsAdapterImpl;
import com.baeldung.designpatterns.adapter.McLaren;

public class AdapterPatternIntegrationTest {
    @Test
    public void givenLuxuryCarsAdapter_WhenConvertingMPHToKMPH_thenSuccessfullyConverted() {
        assertEquals(new LuxuryCarsAdapterImpl(new BugattiVeyron()).getSpeed(), 431.30312, 0.00001);
        assertEquals(new LuxuryCarsAdapterImpl(new McLaren()).getSpeed(), 387.85094, 0.00001);
        assertEquals(new LuxuryCarsAdapterImpl(new AstonMartin()).getSpeed(), 354.0548, 0.00001);
    }
}

