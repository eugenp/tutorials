package com.baeldung.algorithms;

import java.util.Scanner;

import com.baeldung.algorithms.ga.annealing.SimulatedAnnealing;
import com.baeldung.algorithms.ga.ant_colony.AntColonyOptimization;
import com.baeldung.algorithms.ga.binary.SimpleGeneticAlgorithm;
import com.baeldung.algorithms.slope_one.SlopeOne;

public class RunAlgorithm {

	public static void main(String[] args) throws InstantiationException, IllegalAccessException {
		Scanner in = new Scanner(System.in);
		System.out.println("Run algorithm:");
		System.out.println("1 - Simulated Annealing");
		System.out.println("2 - Slope One");
		System.out.println("3 - Simple Genetic Algorithm");
		System.out.println("4 - Ant Colony");
		System.out.println("5 - Dijkstra");
		System.out.println("6 - All pairs in an array that add up to a given sum");
		int decision = in.nextInt();
		switch (decision) {
		case 1:
			System.out.println(
					"Optimized distance for travel: " + SimulatedAnnealing.simulateAnnealing(10, 10000, 0.9995));
			break;
		case 2:
			SlopeOne.slopeOne(3);
			break;
		case 3:
			SimpleGeneticAlgorithm ga = new SimpleGeneticAlgorithm();
			ga.runAlgorithm(50, "1011000100000100010000100000100111001000000100000100000000001111");
			break;
		case 4:
			AntColonyOptimization antColony = new AntColonyOptimization(21);
			antColony.startAntOptimization();
			break;
		case 5:
			System.out.println("Please run the DijkstraAlgorithmTest.");
			break;
		default:
			System.out.println("Unknown option");
			break;
		}
		in.close();
	}

}
