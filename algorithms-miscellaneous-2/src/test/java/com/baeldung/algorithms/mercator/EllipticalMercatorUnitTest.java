package com.baeldung.algorithms.mercator;

import org.junit.Test;


public class EllipticalMercatorUnitTest {

    @Test
    public void givenThatTheInputIs22_whenXAxisProjectionIsCalled_thenTheResultIsTheCorrectOne() {
        Mercator mercator = new EllipticalMercator();
        double result = mercator.xAxisProjection(22);
        assert result == 2449028.7974520186;
    }

    @Test
    public void givenThatTheInputIs44_whenYAxisProjectionIsCalled_thenTheResultIsTheCorrectOne() {
        Mercator mercator = new EllipticalMercator();
        double result = mercator.yAxisProjection(44);
        assert result == 5435749.887511954;
    }
}
