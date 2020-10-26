package com.baeldung.guava;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.BiMap;
import com.google.common.collect.HashBasedTable;
import com.google.common.collect.HashBiMap;
import com.google.common.collect.Multimap;
import com.google.common.collect.Table;

public class GuavaUnitTest {
    private final static BiMap<Integer, String> daysOfWeek = HashBiMap.create();
    private final static Multimap<String, String> groceryCart = ArrayListMultimap.create();
    private final static Table<String, String, String> cityCoordinates = HashBasedTable.create();
    private final static Table<String, String, String> movies = HashBasedTable.create();

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

        cityCoordinates.put("40.7128° N", "74.0060° W", "New York");
        cityCoordinates.put("48.8566° N", "2.3522° E", "Paris");
        cityCoordinates.put("19.0760° N", "72.8777° E", "Mumbai");

        movies.put("Tom Hanks", "Meg Ryan", "You've Got Mail");
        movies.put("Tom Hanks", "Catherine Zeta-Jones", "The Terminal");
        movies.put("Bradley Cooper", "Lady Gaga", "A Star is Born");
        movies.put("Keenu Reaves", "Sandra Bullock", "Speed");
        movies.put("Tom Hanks", "Sandra Bullock", "Extremely Loud & Incredibly Close");
    }

    @Test
    public void givenBiMap_whenValue_thenKeyReturned() {
        assertEquals(Integer.valueOf(7), daysOfWeek.inverse()
            .get("Sunday"));
    }

    @Test
    public void givenBiMap_whenKey_thenValueReturned() {
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

        groceryCart.remove("Fruits", "Apple");
        assertEquals(4, groceryCart.size());

        groceryCart.removeAll("Fruits");
        assertEquals(2, groceryCart.size());
    }

    @Test
    public void givenCoordinatesTable_whenFetched_thenOK() {

        List<String> expectedLongitudes = Arrays.asList("74.0060° W", "2.3522° E", "72.8777° E");

        assertArrayEquals(expectedLongitudes.toArray(), cityCoordinates.columnKeySet()
            .toArray());

        List<String> expectedCities = Arrays.asList("New York", "Paris", "Mumbai");

        assertArrayEquals(expectedCities.toArray(), cityCoordinates.values()
            .toArray());

        assertTrue(cityCoordinates.rowKeySet()
            .contains("48.8566° N"));

    }

    @Test
    public void givenMoviesTable_whenFetched_thenOK() {
        assertEquals(3, movies.row("Tom Hanks")
            .size());

        assertEquals(2, movies.column("Sandra Bullock")
            .size());

        assertEquals("A Star is Born", movies.get("Bradley Cooper", "Lady Gaga"));

        assertTrue(movies.containsValue("Speed"));

    }
}