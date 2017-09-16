package com.baeldung.osgi.geocoding.mapquest;

import com.baeldung.osgi.geocoding.mapquest.MapQuestGeocoder;
import com.baeldung.osgi.geocoding.service.Coord;
import com.baeldung.osgi.geocoding.service.GeocodeException;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class MapQuestGeocoderTest {

    @Test public void shouldExecuteAConversion() throws GeocodeException {

        // given
        MapQuestGeocoder sut = new MapQuestGeocoder();

        // when
        Coord coord = sut.geocode("Colico 12, Milan, Italy");

        // then
        assertEquals("(45.500190, 9.161920)", coord.toString());

    }

}