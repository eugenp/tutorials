package com.baeldung.map.hashing.updates;

import com.baeldung.map.hashing.updates.objects.Fruit;
import org.junit.jupiter.api.*;

import java.util.*;
import java.util.function.BiFunction;

import static org.junit.Assert.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UpdateMethodUnitTest {

    Map<String, Double> fruitMap = new HashMap<>();

    @BeforeAll
    void setup() {
        fruitMap.put(Fruit.APPLE.name(), 2.45);
        fruitMap.put(Fruit.GRAPES.name(), 1.22);
        fruitMap.put(Fruit.WATERMELON.name(), 1.42);
        fruitMap.put(Fruit.LEMON.name(), 3.21);
        fruitMap.put(Fruit.PLUM.name(), 4.34);
    }

    @Test
    public void givenFruitMap_whenPuttingAList_thenHashMapUpdatesAndInsertsValues() {

        List<Fruit> fruits = new ArrayList<>();
        fruits.add(Fruit.APPLE);
        fruits.add(Fruit.PINEAPPLE);

        Double newValue = 2.11;

        UpdateMethod.updateWithPut(fruitMap, fruits, newValue);

        assertEquals(
                fruitMap.get(Fruit.APPLE.name()),
                newValue
        );
        assertTrue(fruitMap.containsKey(Fruit.PINEAPPLE.name()));
        assertEquals(
                fruitMap.get(Fruit.PINEAPPLE.name()),
                newValue
        );
    }

    @Test
    public void givenFruitMap_whenApplyingDiscount_thenValuesReduced() {

        double discount = 0.15d;

        List<String> discountFruits = new ArrayList<>();
        discountFruits.add(Fruit.GRAPES.name());
        discountFruits.add(Fruit.LEMON.name());

        double grapesDiscountedValue = fruitMap.get(Fruit.GRAPES.name()) * (1 - discount);
        double lemonDiscountedValue = fruitMap.get(Fruit.LEMON.name()) * (1 - discount);

        UpdateMethod.applyDiscount(discountFruits, fruitMap, discount);

        assertEquals(
                fruitMap.get(Fruit.GRAPES.name()),
                Double.valueOf(grapesDiscountedValue)
        );
        assertEquals(
                fruitMap.get(Fruit.LEMON.name()),
                Double.valueOf(lemonDiscountedValue)
        );
    }

    @Test
    public void givenFruitMap_whenReplacingOldValue_thenNewValueSet() {

        Double plumPrice = fruitMap.get(Fruit.PLUM.name());

        Double oldValue = UpdateMethod.replace(fruitMap, Fruit.PLUM);

        assertNotNull(oldValue);
        assertEquals(plumPrice, oldValue);
        assertEquals(fruitMap.get(Fruit.PLUM.name()), Double.valueOf(3.22));
    }

    @Test
    public void givenFruitMap_whenReplacingWithRealOldValue_thenNewValueSet() {

        Double plumPrice = fruitMap.get(Fruit.PLUM.name());
        boolean isUpdated = UpdateMethod.replace2(fruitMap, Fruit.PLUM, plumPrice);

        assertTrue(isUpdated);
    }

    @Test
    public void givenFruitMap_whenReplacingWithWrongOldValue_thenNewValueSet() {

        boolean isUpdated = UpdateMethod.replace2(fruitMap, Fruit.PLUM, 0);
        assertFalse(isUpdated);

    }

    @Test
    public void givenFruitMap_whenReplacingAllValues_thenNewValueSet() {

        BiFunction<Double, Integer, Double> powFunction = (x1, x2) -> Math.pow(x1, x2);
        Double oldAppleValue = fruitMap.get(Fruit.APPLE.name());

        UpdateMethod.replacell(fruitMap, powFunction);

        assertEquals(fruitMap.get(Fruit.APPLE.name()), Double.valueOf(Math.pow(oldAppleValue, 2)));

    }

    @Test
    public void givenFruitMap_whenGetOrDefaultUsedWithPut_thenNewEntriesAdded() {

        List<Fruit> fruits = new ArrayList<>();
        fruits.add(Fruit.APPLE);
        fruits.add(Fruit.ORANGE);
        UpdateMethod.getOrDefault(fruitMap, fruits);

        assertTrue(fruitMap.containsKey(Fruit.ORANGE.name()));
        assertEquals(fruitMap.get(Fruit.ORANGE.name()), Double.valueOf(2.41));

    }

    @Test
    public void givenFruitMap_whenPutIfAbsentUsed_thenNewEntriesAdded() {

        Map<Fruit, Double> newFruits = new HashMap<>();
        newFruits.put(Fruit.PEAR, null);

        newFruits.put(Fruit.PEACH, 1.87);
        newFruits.put(Fruit.APPLE, 1.78);
        newFruits.put(Fruit.PEAR, 2.04);

        UpdateMethod.putIfAbsent(fruitMap, newFruits);

        assertTrue(fruitMap.containsKey(Fruit.PEACH.name()));
        assertTrue(fruitMap.containsKey(Fruit.PEAR.name()));
        assertEquals(fruitMap.get(Fruit.PEACH.name()), Double.valueOf(1.87));
        assertEquals(fruitMap.get(Fruit.PEAR.name()), Double.valueOf(2.04));

    }

    @Test
    public void givenFruitMap_whenComputeUsed_thenValueUpdated() {

        List<Fruit> fruits = new ArrayList<>();
        fruits.add(Fruit.BLUEBERRY);
        fruits.add(Fruit.GRAPES);

        Double oldOrangeValue = fruitMap.get(Fruit.GRAPES.name());
        BiFunction<Double, Integer, Double> powFunction = (x1, x2) -> Math.pow(x1, x2);

        UpdateMethod.compute(fruitMap, powFunction, fruits);

        assertFalse(fruitMap.containsKey(Fruit.BLUEBERRY.name()));
        assertEquals(fruitMap.get(Fruit.GRAPES.name()), Double.valueOf(Math.pow(oldOrangeValue, 2)));

    }

    @Test
    public void givenFruitMap_whenComputeIfAbsentUsed_thenNewEntriesAdded() {

        List<Fruit> fruits = new ArrayList<>();
        fruits.add(Fruit.BLUEBERRY);
        fruits.add(Fruit.PEACH);

        UpdateMethod.computeIfAbsent(fruitMap, fruits);

        assertTrue(fruitMap.containsKey(Fruit.BLUEBERRY.name()));
        assertEquals(fruitMap.get(Fruit.BLUEBERRY.name()), Double.valueOf(Fruit.BLUEBERRY.name().length()));

    }

    @Test
    public void givenFruitMap_whenComputeIfPresentUsed_thenNewEntriesAdded() {

        List<Fruit> fruits = new ArrayList<>();
        fruits.add(Fruit.APPLE);
        fruits.add(Fruit.BLUEBERRY);

        Double oldAppleValue = fruitMap.get(Fruit.APPLE.name());

        BiFunction<Double, Integer, Double> powFunction = (x1, x2) -> Math.pow(x1, x2);
        UpdateMethod.computeIfPresent(fruitMap, powFunction, fruits);

        assertTrue(fruitMap.containsKey(Fruit.APPLE.name()));
        assertEquals(fruitMap.get(Fruit.APPLE.name()), Double.valueOf(Math.pow(oldAppleValue, 2)));

    }

    @Test
    public void givenFruitMap_whenMergeUsed_thenNewEntriesAdded() {

        List<Fruit> fruits = new ArrayList<>();
        fruits.add(Fruit.STRAWBERRY);
        fruits.add(Fruit.PLUM);
        fruits.add(Fruit.RASPBERRY);

        double defaultValue = 1.25;
        BiFunction<Double, Integer, Double> powFunction = (x1, x2) -> Math.pow(x1, x2);
        UpdateMethod.merge(fruitMap, powFunction, fruits, defaultValue);

        assertTrue(fruitMap.containsKey(Fruit.STRAWBERRY.name()));
        assertTrue(fruitMap.containsKey(Fruit.PLUM.name()));
        assertTrue(fruitMap.containsKey(Fruit.RASPBERRY.name()));
        assertEquals(fruitMap.get(Fruit.STRAWBERRY.name()), Double.valueOf(defaultValue));
        assertEquals(fruitMap.get(Fruit.PLUM.name()), Double.valueOf(Math.pow(defaultValue, 2)));
        assertEquals(fruitMap.get(Fruit.RASPBERRY.name()), Double.valueOf(defaultValue));

    }

}
