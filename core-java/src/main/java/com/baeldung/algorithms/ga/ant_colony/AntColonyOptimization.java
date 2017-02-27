package com.baeldung.algorithms.ga.ant_colony;

import java.util.Arrays;
import java.util.Random;

public class AntColonyOptimization {

	private double c = 1.0;
	private double alpha = 1;
	private double beta = 5;
	private double evaporation = 0.5;
	private double Q = 500;
	private double antFactor = 0.8;
	private double randomFactor = 0.01;

	private int maxIterations = 1000;

	public int numberOfCities;
	public int numberOfAnts;
	private double graph[][];
	private double trails[][];
	private Ant ants[];
	private Random random = new Random();
	private double probabilities[];

	private int currentIndex;

	public int[] bestTourOrder;
	public double bestTourLength;

	public AntColonyOptimization(int noOfCities) {
		graph = generateRandomMatrix(noOfCities);
		numberOfCities = graph.length;
		numberOfAnts = (int) (numberOfCities * antFactor);

		trails = new double[numberOfCities][numberOfCities];
		probabilities = new double[numberOfCities];
		ants = new Ant[numberOfAnts];
		for (int j = 0; j < numberOfAnts; j++) {
			ants[j] = new Ant(numberOfCities);
		}
	}

	/**
	 * Generate initial solution
	 * @param n
	 * @return
	 */
	public double[][] generateRandomMatrix(int n) {
		double[][] randomMatrix = new double[n][n];
		random.setSeed(System.currentTimeMillis());
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				Integer r = random.nextInt(100) + 1;
				randomMatrix[i][j] = Math.abs(r);
			}
		}
		return randomMatrix;
	}
	
	/**
	 * Perform ant optimization
	 * @return
	 */
	public int[] startAntOptimization() {
		int[] finalResult = null;
		for (int i = 1; i <= 3; i++) {
			System.out.println("Attempt #" + i);
			finalResult = solve();
		}
		return finalResult;
	}
	
	/**
	 * Use this method to run the main logic
	 * @return
	 */
	private int[] solve() {
		setupAnts();
		clearTrails();
		int iteration = 0;
		while (iteration < maxIterations) {
			moveAnts();
			updateTrails();
			updateBest();
			iteration++;
		}
		System.out.println("Best tour length: " + (bestTourLength - numberOfCities));
		System.out.println("Best tour order: " + Arrays.toString(bestTourOrder));
		return bestTourOrder.clone();
	}
	
	/**
	 * Prepare ants for the simulation
	 */
	private void setupAnts() {
		currentIndex = -1;
		for (int i = 0; i < numberOfAnts; i++) {
			ants[i].clear();
			ants[i].visitCity(currentIndex, random.nextInt(numberOfCities));
		}
		currentIndex++;
	}
	
	/**
	 * At each iteration, move ants
	 */
	private void moveAnts() {
		while (currentIndex < numberOfCities - 1) {
			for (Ant a : ants)
				a.visitCity(currentIndex, selectNextCity(a));
			currentIndex++;
		}
	}
	
	/**
	 * Select next city for each ant
	 * @param ant
	 * @return
	 */
	private int selectNextCity(Ant ant) {
		if (random.nextDouble() < randomFactor) {
			int t = random.nextInt(numberOfCities - currentIndex);
			int j = -1;
			for (int i = 0; i < numberOfCities; i++) {
				if (!ant.visited(i)) {
					j++;
				}
				if (j == t) {
					return i;
				}
			}
		}
		calculateProbabilities(ant);
		double r = random.nextDouble();
		double total = 0;
		for (int i = 0; i < numberOfCities; i++) {
			total += probabilities[i];
			if (total >= r) {
				return i;
			}
		}

		throw new RuntimeException("There are no other cities");
	}

	/**
	 * Calculate the next city picks probabilites
	 * @param ant
	 */
	private void calculateProbabilities(Ant ant) {
		int i = ant.trail[currentIndex];
		double pheromone = 0.0;
		for (int l = 0; l < numberOfCities; l++) {
			if (!ant.visited(l)) {
				pheromone += Math.pow(trails[i][l], alpha) * Math.pow(1.0 / graph[i][l], beta);
			}
		}
		for (int j = 0; j < numberOfCities; j++) {
			if (ant.visited(j)) {
				probabilities[j] = 0.0;
			} else {
				double numerator = Math.pow(trails[i][j], alpha) * Math.pow(1.0 / graph[i][j], beta);
				probabilities[j] = numerator / pheromone;
			}
		}
	}

	/**
	 * Update trails that ants used
	 */
	private void updateTrails() {
		for (int i = 0; i < numberOfCities; i++) {
			for (int j = 0; j < numberOfCities; j++) {
				trails[i][j] *= evaporation;
			}
		}
		for (Ant a : ants) {
			double contribution = Q / a.trailLength(graph);
			for (int i = 0; i < numberOfCities - 1; i++) {
				trails[a.trail[i]][a.trail[i + 1]] += contribution;
			}
			trails[a.trail[numberOfCities - 1]][a.trail[0]] += contribution;
		}
	}

	/**
	 * Update the best solution
	 */
	private void updateBest() {
		if (bestTourOrder == null) {
			bestTourOrder = ants[0].trail;
			bestTourLength = ants[0].trailLength(graph);
		}
		for (Ant a : ants) {
			if (a.trailLength(graph) < bestTourLength) {
				bestTourLength = a.trailLength(graph);
				bestTourOrder = a.trail.clone();
			}
		}
	}

	/**
	 * Clear trails after simulation
	 */
	private void clearTrails() {
		for (int i = 0; i < numberOfCities; i++)
			for (int j = 0; j < numberOfCities; j++)
				trails[i][j] = c;
	}

}
