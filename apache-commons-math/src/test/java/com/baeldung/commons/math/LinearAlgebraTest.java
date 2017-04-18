package com.baeldung.commons.math;

import org.apache.commons.math3.linear.*;
import org.junit.Test;

public class LinearAlgebraTest {

    @Test
    public void testSolve() {
        RealMatrix a =
                new Array2DRowRealMatrix(new double[][] { { 2, 3, -2 }, { -1, 7, 6 }, { 4, -3, -5 } },
                        false);
        RealVector b = new ArrayRealVector(new double[] { 1, -2, 1 }, false);

        DecompositionSolver solver = new LUDecomposition(a).getSolver();

        RealVector solution = solver.solve(b);

        System.out.println(solution);
    }

}
