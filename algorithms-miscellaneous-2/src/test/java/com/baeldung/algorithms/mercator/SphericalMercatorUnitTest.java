package com.baeldung.algorithms.mercator;

import org.junit.Test;
import static junit.framework.TestCase.assertEquals;

public class SphericalMercatorUnitTest {

    @Test
    public void giventThatTheInputIs22_whenXAxisProjectionIsCalled_thenTheResultIsTheCorrectOne() {
        Mercator mercator = new SphericalMercator();
        double result = mercator.xAxisProjection(22);
        assertEquals(result, 2449028.7974520186);
    }

    @Test
    public void giventThatTheInputIs44_whenYAxisProjectionIsCalled_thenTheResultIsTheCorrectOne() {
        Mercator mercator = new SphericalMercator();
        double result = mercator.yAxisProjection(44);
        assertEquals(result, 5465442.183322753);
    }
}
