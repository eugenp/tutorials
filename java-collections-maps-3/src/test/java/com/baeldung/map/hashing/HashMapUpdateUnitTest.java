package com.baeldung.map.hashing;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class HashMapUpdateUnitTest {

    Map<String, Double> fruitMap = new HashMap<>();

    @BeforeAll
    void setup() {
        fruitMap.put("apple", 2.45);
        fruitMap.put("grapes", 1.22);
    }

    @Test
    public void givenFruitMap_whenPuttingAList_thenHashMapUpdatesAndInsertsValues() {
        Double newValue = 2.11;
        fruitMap.put("apple", newValue);
        fruitMap.put("orange", newValue);
        Assertions.assertEquals(newValue, fruitMap.get("apple"));
        Assertions.assertTrue(fruitMap.containsKey("orange"));
        Assertions.assertEquals(newValue, fruitMap.get("orange"));
    }

    @Test
    public void givenFruitMap_whenKeyExists_thenValuesUpdated() {
        double newValue = 2.31;
        if (fruitMap.containsKey("apple")) {
            fruitMap.put("apple", newValue);
        }
        Assertions.assertEquals(Double.valueOf(newValue), fruitMap.get("apple"));
    }

    @Test
    public void givenFruitMap_whenReplacingOldValue_thenNewValueSet() {
        double newPrice = 3.22;
        Double applePrice = fruitMap.get("apple");
        Double oldValue = fruitMap.replace("apple", newPrice);
        Assertions.assertNotNull(oldValue);
        Assertions.assertEquals(oldValue, applePrice);
        Assertions.assertEquals(Double.valueOf(newPrice), fruitMap.get("apple"));
    }

    @Test
    public void givenFruitMap_whenReplacingWithRealOldValue_thenNewValueSet() {
        double newPrice = 3.22;
        Double applePrice = fruitMap.get("apple");
        boolean isUpdated = fruitMap.replace("apple", applePrice, newPrice);
        Assertions.assertTrue(isUpdated);
    }

    @Test
    public void givenFruitMap_whenReplacingWithWrongOldValue_thenNewValueNotSet() {
        double newPrice = 3.22;
        boolean isUpdated = fruitMap.replace("apple", Double.valueOf(0), newPrice);
        Assertions.assertFalse(isUpdated);
    }

    @Test
    public void givenFruitMap_whenGetOrDefaultUsedWithPut_thenNewEntriesAdded() {
        fruitMap.put("plum", fruitMap.getOrDefault("plum", 2.41));
        Assertions.assertTrue(fruitMap.containsKey("plum"));
        Assertions.assertEquals(Double.valueOf(2.41), fruitMap.get("plum"));
    }

    @Test
    public void givenFruitMap_whenPutIfAbsentUsed_thenNewEntriesAdded() {
        double newValue = 1.78;
        fruitMap.putIfAbsent("apple", newValue);
        fruitMap.putIfAbsent("pear", newValue);
        Assertions.assertTrue(fruitMap.containsKey("pear"));
        Assertions.assertNotEquals(Double.valueOf(newValue), fruitMap.get("apple"));
        Assertions.assertEquals(Double.valueOf(newValue), fruitMap.get("pear"));
    }

    @Test
    public void givenFruitMap_whenComputeUsed_thenValueUpdated() {
        double oldPrice = fruitMap.get("apple");
        BiFunction<Double, Integer, Double> powFunction = (x1, x2) -> Math.pow(x1, x2);
        fruitMap.compute(
                "apple", (k, v) -> powFunction.apply(v, 2));
        Assertions.assertEquals(
                Double.valueOf(Math.pow(oldPrice, 2)), fruitMap.get("apple"));
        Assertions.assertThrows(
                NullPointerException.class, () -> fruitMap.compute("blueberry", (k, v) -> powFunction.apply(v, 2)));
    }

    @Test
    public void givenFruitMap_whenComputeIfAbsentUsed_thenNewEntriesAdded() {
        fruitMap.computeIfAbsent(
                "lemon", k -> Double.valueOf(k.length()));
        Assertions.assertTrue(fruitMap.containsKey("lemon"));
        Assertions.assertEquals(Double.valueOf("lemon".length()), fruitMap.get("lemon"));
    }

    @Test
    public void givenFruitMap_whenComputeIfPresentUsed_thenValuesUpdated() {
        Double oldAppleValue = fruitMap.get("apple");
        BiFunction<Double, Integer, Double> powFunction = (x1, x2) -> Math.pow(x1, x2);
        fruitMap.computeIfPresent(
                "apple", (k, v) -> powFunction.apply(v, 2));
        Assertions.assertEquals(Double.valueOf(Math.pow(oldAppleValue, 2)), fruitMap.get("apple"));
    }

    @Test
    public void givenFruitMap_whenMergeUsed_thenNewEntriesAdded() {
        double defaultValue = 1.25;
        BiFunction<Double, Integer, Double> powFunction = (x1, x2) -> Math.pow(x1, x2);
        fruitMap.merge(
                "apple", defaultValue, (k, v) -> powFunction.apply(v, 2));
        fruitMap.merge(
                "strawberry", defaultValue, (k, v) -> powFunction.apply(v, 2));
        Assertions.assertTrue(fruitMap.containsKey("strawberry"));
        Assertions.assertEquals(Double.valueOf(defaultValue), fruitMap.get("strawberry"));
        Assertions.assertEquals(Double.valueOf(Math.pow(defaultValue, 2)), fruitMap.get("apple"));
    }

}
