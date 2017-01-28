package com.baeldung.algorithms;

import java.util.Scanner;

import com.baeldung.algorithms.annealing.SimulatedAnnealing;
import com.baeldung.algorithms.slope_one.SlopeOne;

public class RunAlgorithm {

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		System.out.println("Run algorithm:");
		System.out.println("1 - Simulated Annealing");
		System.out.println("2 - Slope One");
		int decision = in.nextInt();
		switch (decision) {
		case 1:
			System.out.println(
					"Optimized distance for travel: " + SimulatedAnnealing.simulateAnnealing(10, 10000, 0.9995));
			break;
		case 2:
			SlopeOne.slopeOne(3);
			break;
		default:
			System.out.println("Unknown option");
			break;
		}
		in.close();
	}

}
