package com.baeldung.algorithms.optimization.lp;

public class AssignmentSolution {

    private double totalCost;
    private double[][] assignment;

    public AssignmentSolution(double totalCost, double[][] assignment) {
        this.totalCost = totalCost;
        this.assignment = assignment;
    }

    public double getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(double totalCost) {
        this.totalCost = totalCost;
    }

    public double[][] getAssignment() {
        return assignment;
    }

    public void setAssignment(double[][] assignment) {
        this.assignment = assignment;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("AssignmentSolution\n");
        sb.append("Total Cost: ").append(totalCost).append("\n");
        sb.append("Assignment Matrix:\n");
        for (int i = 0; i < assignment.length; i++) {
            sb.append("[ ");
            for (int j = 0; j < assignment[i].length; j++) {
                sb.append(String.format("%.0f", assignment[i][j]));
                if (j < assignment[i].length - 1) {
                    sb.append(", ");
                }
            }
            sb.append(" ]\n");
        }
        return sb.toString();
    }
}
