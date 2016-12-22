package com.baeldung.algorithms.slope_one;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

/**
 * Slope One algorithm implementation
 */
public class SlopeOne {

	private static Map<Item, Map<Item, Double>> differencesMatrix = new HashMap<>();
	private static Map<Item, Map<Item, Integer>> frequenciesMatrix = new HashMap<>();
	private static Map<User, HashMap<Item, Double>> inputData;
	private static Map<User, HashMap<Item, Double>> outputData = new HashMap<>();

	public static void slopeOne(int numberOfUsers) {
		inputData = InputData.initializeData(numberOfUsers);
		System.out.println("Slope One - Before the Prediction\n");
		buildDifferencesMatrix(inputData);
		System.out.println("\nSlope One - With Predictions\n");
		predict(inputData);
	}

	/**
	 * Based on the available data, calculate the relationships between the
	 * items and number of occurences
	 * 
	 * @param data existing user data and their items' ratings
	 */
	private static void buildDifferencesMatrix(Map<User, HashMap<Item, Double>> data) {
		for (HashMap<Item, Double> user : data.values()) {
			for (Entry<Item, Double> entry : user.entrySet()) {
				if (!differencesMatrix.containsKey(entry.getKey())) {
					differencesMatrix.put(entry.getKey(), new HashMap<Item, Double>());
					frequenciesMatrix.put(entry.getKey(), new HashMap<Item, Integer>());
				}
				for (Entry<Item, Double> entry2 : user.entrySet()) {
					int oldCount = 0;
					if (frequenciesMatrix.get(entry.getKey()).containsKey(entry2.getKey()))
						oldCount = frequenciesMatrix.get(entry.getKey()).get(entry2.getKey()).intValue();
					double oldDiff = 0.0;
					if (differencesMatrix.get(entry.getKey()).containsKey(entry2.getKey()))
						oldDiff = differencesMatrix.get(entry.getKey()).get(entry2.getKey()).doubleValue();
					double observedDiff = entry.getValue() - entry2.getValue();
					frequenciesMatrix.get(entry.getKey()).put(entry2.getKey(), oldCount + 1);
					differencesMatrix.get(entry.getKey()).put(entry2.getKey(), oldDiff + observedDiff);
				}
			}
		}
		for (Item j : differencesMatrix.keySet()) {
			for (Item i : differencesMatrix.get(j).keySet()) {
				double oldvalue = differencesMatrix.get(j).get(i).doubleValue();
				int count = frequenciesMatrix.get(j).get(i).intValue();
				differencesMatrix.get(j).put(i, oldvalue / count);
			}
		}
		printData(data);
	}

	/**
	 * Based on existing data predict all missing ratings. If prediction is not
	 * possible, the value will be equal to -1
	 * 
	 * @param data existing user data and their items' ratings
	 */
	private static void predict(Map<User, HashMap<Item, Double>> data) {
		HashMap<Item, Double> predictions = new HashMap<Item, Double>();
		HashMap<Item, Integer> frequencies = new HashMap<Item, Integer>();
		for (Item j : differencesMatrix.keySet()) {
			frequencies.put(j, 0);
			predictions.put(j, 0.0);
		}
		for (Entry<User, HashMap<Item, Double>> entry : data.entrySet()) {
			for (Item j : entry.getValue().keySet()) {
				for (Item k : differencesMatrix.keySet()) {
					try {
						double newValue = (differencesMatrix.get(k).get(j).doubleValue()
								+ entry.getValue().get(j).doubleValue()) * frequenciesMatrix.get(k).get(j).intValue();
						predictions.put(k, predictions.get(k) + newValue);
						frequencies.put(k, frequencies.get(k) + frequenciesMatrix.get(k).get(j).intValue());
					} catch (NullPointerException e) {
					}
				}
			}
			HashMap<Item, Double> cleanPredictions = new HashMap<Item, Double>();
			for (Item j : predictions.keySet()) {
				if (frequencies.get(j) > 0) {
					cleanPredictions.put(j, predictions.get(j).doubleValue() / frequencies.get(j).intValue());
				}
			}
			for (Item j : InputData.items) {
				if (entry.getValue().containsKey(j)) {
					cleanPredictions.put(j, entry.getValue().get(j));
				} else {
					cleanPredictions.put(j, -1.0);
				}
			}
			outputData.put(entry.getKey(), cleanPredictions);
		}
		printData(outputData);
	}

	private static void printData(Map<User, HashMap<Item, Double>> data) {
		for (User user : data.keySet()) {
			System.out.println(user.getUsername() + ":");
			print(data.get(user));
		}
	}

	private static void print(HashMap<Item, Double> hashMap) {
		NumberFormat formatter = new DecimalFormat("#0.000");
		for (Item j : hashMap.keySet()) {
			System.out.println(" " + j.getItemName() + " --> " + formatter.format(hashMap.get(j).doubleValue()));
		}
	}

}
