package com.baeldung.algorithms.optimization.lp;

import java.util.ArrayList;
import java.util.Collection;

import org.apache.commons.math3.optim.PointValuePair;
import org.apache.commons.math3.optim.linear.LinearConstraint;
import org.apache.commons.math3.optim.linear.LinearConstraintSet;
import org.apache.commons.math3.optim.linear.LinearObjectiveFunction;
import org.apache.commons.math3.optim.linear.NonNegativeConstraint;
import org.apache.commons.math3.optim.linear.Relationship;
import org.apache.commons.math3.optim.linear.SimplexSolver;
import org.apache.commons.math3.optim.nonlinear.scalar.GoalType;

public class CommonsMathAssignmentSolver implements AssignmentSolver {

    public AssignmentSolution solve(double[][] t) {

        int volunteers = t.length;
        int locations = t[0].length;
        int vars = volunteers * locations;

        // Objective function coefficients
        double[] x = new double[vars];
        for (int i = 0; i < volunteers; i++) {
            for (int j = 0; j < locations; j++) {
                x[index(i, j, locations)] = t[i][j];
            }
        }

        LinearObjectiveFunction objective = new LinearObjectiveFunction(x, 0);

        Collection<LinearConstraint> constraints = new ArrayList<>();

        // Each volunteer assigned to exactly one location
        for (int i = 0; i < volunteers; i++) {
            double[] x_i = new double[vars];
            for (int j = 0; j < locations; j++) {
                x_i[index(i, j, locations)] = 1.0;
            }
            constraints.add(new LinearConstraint(x_i, Relationship.EQ, 1.0));
        }

        // Each location gets exactly one volunteer
        for (int j = 0; j < locations; j++) {
            double[] x_j = new double[vars];
            for (int i = 0; i < volunteers; i++) {
                x_j[index(i, j, locations)] = 1.0;
            }
            constraints.add(new LinearConstraint(x_j, Relationship.EQ, 1.0));
        }

        // Solve LP
        SimplexSolver solver = new SimplexSolver();
        PointValuePair solution = solver.optimize(
            objective,
            new LinearConstraintSet(constraints),
            GoalType.MINIMIZE,
            new NonNegativeConstraint(true)
        );

        double totalCost = solution.getValue();
        double[] point = solution.getPoint();

        // Rebuild assignment matrix
        double[][] assignment = new double[volunteers][locations];
        for (int i = 0; i < volunteers; i++) {
            for (int j = 0; j < locations; j++) {
                assignment[i][j] = point[index(i, j, locations)];
            }
        }

        return new AssignmentSolution(totalCost, assignment);
    }

    private int index(int i, int j, int locations) {
        return i * locations + j;
    }

}
