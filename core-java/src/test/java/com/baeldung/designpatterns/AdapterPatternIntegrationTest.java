package com.baeldung.designpatterns;


import static org.junit.Assert.*;

import org.junit.Test;

import com.baeldung.designpatterns.adapter.LuxuryCarsSpeedAdapter;
import com.baeldung.designpatterns.adapter.LuxuryCarsSpeedAdapterImpl;

public class AdapterPatternIntegrationTest {
    @Test
    public void givenLuxuryCarsAdapter_WhenConvertingMPHToKMPH_thenSuccessfullyConverted() {
        LuxuryCarsSpeedAdapter luxuryCars = new LuxuryCarsSpeedAdapterImpl();
        assertEquals(luxuryCars.bugattiVeyronInKMPH(), 431.30312, 0.00001);
        assertEquals(luxuryCars.mcLarenInKMPH(), 387.85094, 0.00001);
        assertEquals(luxuryCars.astonMartinInKMPH(), 354.0548, 0.00001);
    }
}

