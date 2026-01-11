package com.baeldung.algorithms.optimization.lp;

import org.ojalgo.optimisation.ExpressionsBasedModel;
import org.ojalgo.optimisation.Expression;
import org.ojalgo.optimisation.Variable;

public class OjAlgoAssignmentSolver implements AssignmentSolver {

    public AssignmentSolution solve(double[][] t) {

        int volunteers = t.length;
        int locations = t[0].length;

        ExpressionsBasedModel model = new ExpressionsBasedModel();
        Variable[][] x = new Variable[volunteers][locations];

        // Create binary decision variables
        for (int i = 0; i < volunteers; i++) {
            for (int j = 0; j < locations; j++) {
                x[i][j] = model
                    .newVariable("Assignment_" + i + "_" + j)
                    .binary()
                    .weight(t[i][j]);
            }
        }

        // Each volunteer is assigned to exactly one location
        for (int i = 0; i < volunteers; i++) {
            Expression volunteerConstraint = model.addExpression("Volunteer_" + i).level(1);
            for (int j = 0; j < locations; j++) {
                volunteerConstraint.set(x[i][j], 1);
            }
        }

        // Each location gets exactly one volunteer
        for (int j = 0; j < locations; j++) {
            Expression locationConstraint = model.addExpression("Location_" + j).level(1);
            for (int i = 0; i < volunteers; i++) {
                locationConstraint.set(x[i][j], 1);
            }
        }

        // Solve
        var result = model.minimise();
        double totalCost = result.getValue();

        // Extract assignment matrix
        double[][] assignment = new double[volunteers][locations];
        for (int i = 0; i < volunteers; i++) {
            for (int j = 0; j < locations; j++) {
                assignment[i][j] = x[i][j].getValue().doubleValue();
            }
        }

        return new AssignmentSolution(totalCost, assignment);
    }
}
