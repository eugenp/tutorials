package com.baeldung.algorithms.mercator;

import org.junit.Assert;
import org.junit.Test;

public class SphericalMercatorUnitTest {

    @Test
    public void giventThatTheInputIs22_whenXAxisProjectionIsCalled_thenTheResultIsTheCorrectOne() {
        Mercator mercator = new SphericalMercator();
        double result = mercator.xAxisProjection(22);
        Assert.assertEquals(result, 2449028.7974520186, 0.0);
    }

    @Test
    public void giventThatTheInputIs44_whenYAxisProjectionIsCalled_thenTheResultIsTheCorrectOne() {
        Mercator mercator = new SphericalMercator();
        double result = mercator.yAxisProjection(44);
        Assert.assertEquals(result, 5465442.183322753, 0.0);
    }
}
