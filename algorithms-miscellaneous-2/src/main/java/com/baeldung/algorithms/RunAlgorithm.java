package com.baeldung.algorithms;

import java.util.Scanner;

import com.baeldung.algorithms.slope_one.SlopeOne;

public class RunAlgorithm {

	public static void main(String[] args) throws InstantiationException, IllegalAccessException {
		Scanner in = new Scanner(System.in);
		System.out.println("1 - Slope One");
		System.out.println("2 - Dijkstra");
		int decision = in.nextInt();
		switch (decision) {
		case 1:
			SlopeOne.slopeOne(3);
			break;
		case 2:
			System.out.println("Please run the DijkstraAlgorithmLongRunningUnitTest.");
			break;
		default:
			System.out.println("Unknown option");
			break;
		}
		in.close();
	}

}
