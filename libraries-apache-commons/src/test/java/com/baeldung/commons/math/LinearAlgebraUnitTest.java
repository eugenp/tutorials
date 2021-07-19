package com.baeldung.commons.math;

import org.apache.commons.math3.linear.*;
import org.junit.Assert;
import org.junit.Test;

public class LinearAlgebraUnitTest {

    @Test
    public void whenDecompositionSolverSolve_thenCorrect() {
        RealMatrix a = new Array2DRowRealMatrix(new double[][] { { 2, 3, -2 }, { -1, 7, 6 }, { 4, -3, -5 } }, false);
        RealVector b = new ArrayRealVector(new double[] { 1, -2, 1 }, false);

        DecompositionSolver solver = new LUDecomposition(a).getSolver();

        RealVector solution = solver.solve(b);

        Assert.assertEquals(-0.3698630137, solution.getEntry(0), 1e-7);
        Assert.assertEquals(0.1780821918, solution.getEntry(1), 1e-7);
        Assert.assertEquals(-0.602739726, solution.getEntry(2), 1e-7);
    }

}
