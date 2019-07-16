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

    private static Map<Item, Map<Item, Double>> diff = new HashMap<>();
    private static Map<Item, Map<Item, Integer>> freq = new HashMap<>();
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
     * @param data
     *            existing user data and their items' ratings
     */
    private static void buildDifferencesMatrix(Map<User, HashMap<Item, Double>> data) {
        for (HashMap<Item, Double> user : data.values()) {
            for (Entry<Item, Double> e : user.entrySet()) {
                if (!diff.containsKey(e.getKey())) {
                    diff.put(e.getKey(), new HashMap<Item, Double>());
                    freq.put(e.getKey(), new HashMap<Item, Integer>());
                }
                for (Entry<Item, Double> e2 : user.entrySet()) {
                    int oldCount = 0;
                    if (freq.get(e.getKey()).containsKey(e2.getKey())) {
                        oldCount = freq.get(e.getKey()).get(e2.getKey()).intValue();
                    }
                    double oldDiff = 0.0;
                    if (diff.get(e.getKey()).containsKey(e2.getKey())) {
                        oldDiff = diff.get(e.getKey()).get(e2.getKey()).doubleValue();
                    }
                    double observedDiff = e.getValue() - e2.getValue();
                    freq.get(e.getKey()).put(e2.getKey(), oldCount + 1);
                    diff.get(e.getKey()).put(e2.getKey(), oldDiff + observedDiff);
                }
            }
        }
        for (Item j : diff.keySet()) {
            for (Item i : diff.get(j).keySet()) {
                double oldValue = diff.get(j).get(i).doubleValue();
                int count = freq.get(j).get(i).intValue();
                diff.get(j).put(i, oldValue / count);
            }
        }
        printData(data);
    }

    /**
     * Based on existing data predict all missing ratings. If prediction is not
     * possible, the value will be equal to -1
     * 
     * @param data
     *            existing user data and their items' ratings
     */
    private static void predict(Map<User, HashMap<Item, Double>> data) {
        HashMap<Item, Double> uPred = new HashMap<Item, Double>();
        HashMap<Item, Integer> uFreq = new HashMap<Item, Integer>();
        for (Item j : diff.keySet()) {
            uFreq.put(j, 0);
            uPred.put(j, 0.0);
        }
        for (Entry<User, HashMap<Item, Double>> e : data.entrySet()) {
            for (Item j : e.getValue().keySet()) {
                for (Item k : diff.keySet()) {
                    try {
                        double predictedValue = diff.get(k).get(j).doubleValue() + e.getValue().get(j).doubleValue();
                        double finalValue = predictedValue * freq.get(k).get(j).intValue();
                        uPred.put(k, uPred.get(k) + finalValue);
                        uFreq.put(k, uFreq.get(k) + freq.get(k).get(j).intValue());
                    } catch (NullPointerException e1) {
                    }
                }
            }
            HashMap<Item, Double> clean = new HashMap<Item, Double>();
            for (Item j : uPred.keySet()) {
                if (uFreq.get(j) > 0) {
                    clean.put(j, uPred.get(j).doubleValue() / uFreq.get(j).intValue());
                }
            }
            for (Item j : InputData.items) {
                if (e.getValue().containsKey(j)) {
                    clean.put(j, e.getValue().get(j));
                } else {
                    clean.put(j, -1.0);
                }
            }
            outputData.put(e.getKey(), clean);
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
