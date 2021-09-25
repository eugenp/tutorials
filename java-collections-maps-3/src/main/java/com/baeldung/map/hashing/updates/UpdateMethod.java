package com.baeldung.map.hashing.updates;

import com.baeldung.map.hashing.updates.objects.Fruit;

import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.BiFunction;

public class UpdateMethod {

    public static void applyDiscount(List<String> discountFruits, Map<String, Double> priceMap, double discount) {

        for (String fruit : discountFruits) {
            if (priceMap.containsKey(fruit)) {
                priceMap.put(fruit, priceMap.get(fruit) * (1 - discount));
            }
        }

    }

    public static void updateWithPut(
            Map<String, Double> priceMap,
            List<Fruit> fruits,
            double newValue) {

        for(Fruit fruit : fruits){
            priceMap.put(fruit.name(), newValue);
        }

    }

    public static Double replace(Map<String, Double> priceMap, Fruit fruit) {
        return priceMap.replace(fruit.name(), 3.22);
    }

    public static boolean replace2(Map<String, Double> priceMap, Fruit fruit, double oldValue) {
        return priceMap.replace(fruit.name(), oldValue, 4.15);
    }

    public static void replacell(
            Map<String, Double> priceMap,
            BiFunction<Double, Integer, Double> powFunction) {

        priceMap.replaceAll((k, v) -> powFunction.apply(v, 2));

    }

    public static void getOrDefault(
            Map<String, Double> priceMap,
            List<Fruit> fruits) {

        for(Fruit fruit : fruits) {
            priceMap.put(
                    fruit.name(),
                    priceMap.getOrDefault(fruit.name(), 2.41)
            );
        }
    }

    public static void putIfAbsent(
            Map<String, Double> priceMap,
            Map<Fruit, Double> newEntries) {

        for (Map.Entry<Fruit, Double> entry : newEntries.entrySet()) {
            priceMap.putIfAbsent(entry.getKey().name(), entry.getValue());
        }

    }

    public static void compute(
            Map<String, Double> priceMap,
            BiFunction powFunction,
            List<Fruit> fruits) {

        for (Fruit fruit : fruits) {
            try {
                priceMap.compute(
                        fruit.name(),
                        (k, v) -> (Double) powFunction.apply(v, 2));
            } catch (NullPointerException ex) {
                System.out.println(String.format("Fruit %s doesn't exist in the map!", fruit.name()));
            }
        }

    }

    public static void computeIfAbsent(
            Map<String, Double> priceMap,
            List<Fruit> fruits) {

        for (Fruit fruit : fruits) {
            priceMap.computeIfAbsent(
                    fruit.name(),
                    k -> Double.valueOf(k.length()));
        }

    }

    public static void computeIfPresent(
            Map<String, Double> priceMap,
            BiFunction<Double, Integer, Double> powFunction,
            List<Fruit> fruits) {

        for (Fruit fruit : fruits) {
            priceMap.computeIfPresent(
                    fruit.name(),
                    (k, v) -> powFunction.apply(v, 2));
        }
    }

    public static void merge(
            Map<String, Double> priceMap,
            BiFunction<Double, Integer, Double> powFunction,
            List<Fruit> fruits,
            Double defaultValue) {

        for (Fruit fruit : fruits) {
            priceMap.merge(
                    fruit.name(),
                    defaultValue,
                    (k, v) -> powFunction.apply(v, 2));
        }
    }

    public static void updateAge(Map<String, AtomicInteger> students) {

        students.replaceAll(
                (k,v) -> (
                        v == null ? new AtomicInteger(0) : new AtomicInteger(v.incrementAndGet()))
        );

    }

    public static void updateAge(Map<String, AtomicInteger> students, int years) {

        students.replaceAll(
                (k,v) -> (v == null ? new AtomicInteger(0) : new AtomicInteger(v.addAndGet(years)))
        );

    }
}
