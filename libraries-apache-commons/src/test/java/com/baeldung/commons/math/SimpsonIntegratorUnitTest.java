package com.baeldung.commons.math;

import org.apache.commons.math3.analysis.UnivariateFunction;
import org.apache.commons.math3.analysis.integration.SimpsonIntegrator;
import org.apache.commons.math3.analysis.integration.UnivariateIntegrator;
import org.junit.Assert;
import org.junit.Test;

public class SimpsonIntegratorUnitTest {

    @Test
    public void whenUnivariateIntegratorIntegrate_thenCorrect() {
        final UnivariateFunction function = v -> v;
        final UnivariateIntegrator integrator = new SimpsonIntegrator(1.0e-12, 1.0e-8, 1, 32);

        final double i = integrator.integrate(100, function, 0, 10);

        Assert.assertEquals(16 + 2d / 3d, i, 1e-7);
    }

}
