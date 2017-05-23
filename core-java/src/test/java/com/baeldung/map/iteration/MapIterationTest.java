package com.baeldung.map.iteration;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.junit.BeforeClass;
import org.junit.Test;

public class MapIterationTest {

    public static Map<String, Integer> testMap = new HashMap<String, Integer>();
    public static String testString1 = "One:1";
    public static String testString2 = "Two:2";
    public static String testString3 = "Three:3";

    @BeforeClass
    public static void createTestData() {
        testMap.put("One", 1);
        testMap.put("Three", 3);
        testMap.put("Two", 2);
    }

    @Test
    public void iterateUsingEntrySet_test() {
        MapIteration mapIteration = new MapIteration();
        ArrayList<String> list = mapIteration.iterateUsingEntrySet(testMap);
        assertTrue((list.contains(testString1)) && (list.contains(testString2)) && (list.contains(testString3)));
    }

    @Test
    public void iterateUsingLambda_test() {
        MapIteration mapIteration = new MapIteration();
        ArrayList<String> list = mapIteration.iterateUsingLambda(testMap);
        assertTrue((list.contains(testString1)) && (list.contains(testString2)) && (list.contains(testString3)));
    }

    @Test
    public void iterateUsingIteratorAndEntry_test() {
        MapIteration mapIteration = new MapIteration();
        ArrayList<String> list = mapIteration.iterateUsingIteratorAndEntry(testMap);
        assertTrue((list.contains(testString1)) && (list.contains(testString2)) && (list.contains(testString3)));
    }

    @Test
    public void iterateUsingKeySetAndForeach_test() {
        MapIteration mapIteration = new MapIteration();
        ArrayList<String> list = mapIteration.iterateUsingKeySetAndForeach(testMap);
        assertTrue((list.contains(testString1)) && (list.contains(testString2)) && (list.contains(testString3)));
    }

    @Test
    public void iterateUsingStreamAPI_test() {
        MapIteration mapIteration = new MapIteration();
        ArrayList<String> list = mapIteration.iterateUsingStreamAPI(testMap);
        assertTrue((list.contains(testString1)) && (list.contains(testString2)) && (list.contains(testString3)));
    }

    @Test
    public void iterateKeys_test() {
        MapIteration mapIteration = new MapIteration();
        ArrayList<String> list = mapIteration.iterateKeys(testMap);
        assertTrue((list.contains("One")) && (list.contains("Two")) && (list.contains("Three")));
    }

}
