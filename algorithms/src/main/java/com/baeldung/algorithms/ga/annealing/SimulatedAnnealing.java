package com.baeldung.algorithms.ga.annealing;

public class SimulatedAnnealing {

    private static Travel travel = new Travel(10);

    public static double simulateAnnealing(double startingTemperature, int numberOfIterations, double coolingRate) {
        System.out.println("Starting SA with temperature: " + startingTemperature + ", # of iterations: " + numberOfIterations + " and colling rate: " + coolingRate);
        double t = startingTemperature;
        travel.generateInitialTravel();
        double bestDistance = travel.getDistance();
        System.out.println("Initial distance of travel: " + bestDistance);
        Travel bestSolution = travel;
        Travel currentSolution = bestSolution;

        for (int i = 0; i < numberOfIterations; i++) {
            if (t > 0.1) {
                currentSolution.swapCities();
                double currentDistance = currentSolution.getDistance();
                if (currentDistance < bestDistance) {
                    bestDistance = currentDistance;
                } else if (Math.exp((bestDistance - currentDistance) / t) < Math.random()) {
                    currentSolution.revertSwap();
                }
                t *= coolingRate;
            } else {
                continue;
            }
            if (i % 100 == 0) {
                System.out.println("Iteration #" + i);
            }
        }
        return bestDistance;
    }

}
