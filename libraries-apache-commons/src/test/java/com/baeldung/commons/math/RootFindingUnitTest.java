package com.baeldung.commons.math;

import org.apache.commons.math3.analysis.UnivariateFunction;
import org.apache.commons.math3.analysis.solvers.BracketingNthOrderBrentSolver;
import org.apache.commons.math3.analysis.solvers.UnivariateSolver;
import org.junit.Assert;
import org.junit.Test;

public class RootFindingUnitTest {

    @Test
    public void whenUnivariateSolverSolver_thenCorrect() {
        final UnivariateFunction function = v -> Math.pow(v, 2) - 2;
        UnivariateSolver solver = new BracketingNthOrderBrentSolver(1.0e-12, 1.0e-8, 5);
        double c = solver.solve(100, function, -10.0, 10.0, 0);

        Assert.assertEquals(-Math.sqrt(2), c, 1e-7);
    }

}
