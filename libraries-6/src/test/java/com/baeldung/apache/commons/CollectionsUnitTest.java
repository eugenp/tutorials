package com.baeldung.apache.commons;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.collections4.BidiMap;
import org.apache.commons.collections4.MultiValuedMap;
import org.apache.commons.collections4.bidimap.TreeBidiMap;
import org.apache.commons.collections4.map.MultiKeyMap;
import org.apache.commons.collections4.multimap.ArrayListValuedHashMap;
import org.junit.Test;

public class CollectionsUnitTest {
    private final static BidiMap<Integer, String> daysOfWeek = new TreeBidiMap<Integer, String>();
    private final static MultiValuedMap<String, String> groceryCart = new ArrayListValuedHashMap<>();
    private final static MultiKeyMap<String, String> days = new MultiKeyMap<String, String>();
    private final static MultiKeyMap<String, String> cityCoordinates = new MultiKeyMap<String, String>();

    static {
        daysOfWeek.put(1, "Monday");
        daysOfWeek.put(2, "Tuesday");
        daysOfWeek.put(3, "Wednesday");
        daysOfWeek.put(4, "Thursday");
        daysOfWeek.put(5, "Friday");
        daysOfWeek.put(6, "Saturday");
        daysOfWeek.put(7, "Sunday");

        groceryCart.put("Fruits", "Apple");
        groceryCart.put("Fruits", "Grapes");
        groceryCart.put("Fruits", "Strawberries");
        groceryCart.put("Vegetables", "Spinach");
        groceryCart.put("Vegetables", "Cabbage");

        days.put("Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Weekday");
        days.put("Saturday", "Sunday", "Weekend");

        cityCoordinates.put("40.7128° N", "74.0060° W", "New York");
        cityCoordinates.put("48.8566° N", "2.3522° E", "Paris");
        cityCoordinates.put("19.0760° N", "72.8777° E", "Mumbai");

    }

    @Test
    public void givenBidiMap_whenValue_thenKeyReturned() {
        assertEquals(Integer.valueOf(7), daysOfWeek.inverseBidiMap()
            .get("Sunday"));
    }

    @Test
    public void givenBidiMap_whenKey_thenValueReturned() {
        assertEquals("Tuesday", daysOfWeek.get(2));
    }

    @Test
    public void givenMultiValuedMap_whenFruitsFetched_thenFruitsReturned() {

        List<String> fruits = Arrays.asList("Apple", "Grapes", "Strawberries");
        assertEquals(fruits, groceryCart.get("Fruits"));
    }

    @Test
    public void givenMultiValuedMap_whenVeggiesFetched_thenVeggiesReturned() {
        List<String> veggies = Arrays.asList("Spinach", "Cabbage");
        assertEquals(veggies, groceryCart.get("Vegetables"));
    }

    @Test
    public void givenMultiValuedMap_whenFuitsRemoved_thenVeggiesPreserved() {

        assertEquals(5, groceryCart.size());

        groceryCart.remove("Fruits");
        assertEquals(2, groceryCart.size());
    }

    @Test
    public void givenDaysMultiKeyMap_whenFetched_thenOK() {
        assertFalse(days.get("Saturday", "Sunday")
            .equals("Weekday"));
    }

    @Test
    public void givenCoordinatesMultiKeyMap_whenQueried_thenOK() {
        List<String> expectedLongitudes = Arrays.asList("72.8777° E", "2.3522° E", "74.0060° W");
        List<String> longitudes = new ArrayList<>();

        cityCoordinates.forEach((key, value) -> {
            longitudes.add(key.getKey(1));
        });

        assertArrayEquals(expectedLongitudes.toArray(), longitudes.toArray());

        List<String> expectedCities = Arrays.asList("Mumbai", "Paris", "New York");
        List<String> cities = new ArrayList<>();

        cityCoordinates.forEach((key, value) -> {
            cities.add(value);
        });

        assertArrayEquals(expectedCities.toArray(), cities.toArray());

    }

}