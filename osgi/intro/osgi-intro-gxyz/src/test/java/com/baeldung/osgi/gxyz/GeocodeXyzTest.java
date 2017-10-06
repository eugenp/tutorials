package com.baeldung.osgi.gxyz;

import com.baeldung.osgi.service.Coord;
import com.baeldung.osgi.service.GeocodeException;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class GeocodeXyzTest {

    @Test public void shouldExecuteAConversion() throws GeocodeException {

        // given
        GeocodeXyz sut = new GeocodeXyz();

        // when
        Coord coord = sut.geocode("Colico 12, Milan, Italy");

        // then
        assertEquals("(45.500190, 9.161920)", coord.toString());

    }

}