package com.baeldung;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.baeldung.adapter.AstonMartin;
import com.baeldung.adapter.BugattiVeyron;
import com.baeldung.adapter.McLaren;
import com.baeldung.adapter.Movable;
import com.baeldung.adapter.MovableAdapter;
import com.baeldung.adapter.MovableAdapterImpl;

public class AdapterPatternIntegrationTest {
    @Test
    public void givenMovableAdapter_WhenConvertingMPHToKMPH_thenSuccessfullyConverted() {
        Movable bugattiVeyron = new BugattiVeyron();
        MovableAdapter bugattiVeyronAdapter = new MovableAdapterImpl(bugattiVeyron);
        assertEquals(bugattiVeyronAdapter.getSpeed(), 431.30312, 0.00001);

        Movable mcLaren = new McLaren();
        MovableAdapter mcLarenAdapter = new MovableAdapterImpl(mcLaren);
        assertEquals(mcLarenAdapter.getSpeed(), 387.85094, 0.00001);

        Movable astonMartin = new AstonMartin();
        MovableAdapter astonMartinAdapter = new MovableAdapterImpl(astonMartin);
        assertEquals(astonMartinAdapter.getSpeed(), 354.0548, 0.00001);
    }
}

