package com.baeldung.set;

import org.hamcrest.MatcherAssert;
import org.hamcrest.collection.IsCollectionWithSize;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

public class PowerSetUnitTest {

    @Test
    public void givenSet_WhenPowerSetIsCalculated_ThenItContainsAllSubsets() {
        Set<String> set = RandomSetOfStringGenerator.generateRandomSet();

        Set<Set<String>> powerSet = new PowerSet<String>().recursivePowerSet(set);

        MatcherAssert.assertThat(powerSet, IsCollectionWithSize.hasSize((1 << set.size())));
        Map<String, Integer> counter = new HashMap<>();
        for (Set<String> subset : powerSet) {
            for (String name : subset) {
                int num = counter.getOrDefault(name, 0);
                counter.put(name, num + 1);
            }
        }
        counter.forEach((k, v) -> Assertions.assertEquals((1 << (set.size() - 1)), v.intValue()));
    }

    @Test
    public void givenSet_WhenPowerSetIsCalculatedRecursiveByIndexRepresentation_ThenItContainsAllSubsets() {
        Set<String> set = RandomSetOfStringGenerator.generateRandomSet();

        Set<Set<String>> powerSet = new PowerSet<String>().recursivePowerSetIndexRepresentation(set);

        MatcherAssert.assertThat(powerSet, IsCollectionWithSize.hasSize((1 << set.size())));
        Map<String, Integer> counter = new HashMap<>();
        for (Set<String> subset : powerSet) {
            for (String name : subset) {
                int num = counter.getOrDefault(name, 0);
                counter.put(name, num + 1);
            }
        }
        counter.forEach((k, v) -> Assertions.assertEquals((1 << (set.size() - 1)), v.intValue()));
    }

    @Test
    public void givenSet_WhenPowerSetIsCalculatedRecursiveByBooleanRepresentation_ThenItContainsAllSubsets() {
        Set<String> set = RandomSetOfStringGenerator.generateRandomSet();

        Set<Set<String>> powerSet = new PowerSet<String>().recursivePowerSetBooleanRepresentation(set);

        MatcherAssert.assertThat(powerSet, IsCollectionWithSize.hasSize((1 << set.size())));
        Map<String, Integer> counter = new HashMap<>();
        for (Set<String> subset : powerSet) {
            for (String name : subset) {
                int num = counter.getOrDefault(name, 0);
                counter.put(name, num + 1);
            }
        }
        counter.forEach((k, v) -> Assertions.assertEquals((1 << (set.size() - 1)), v.intValue()));
    }

    @Test
    public void givenSet_WhenPowerSetIsCalculatedIterativePowerSetByLoopOverNumbers_ThenItContainsAllSubsets() {
        Set<String> set = RandomSetOfStringGenerator.generateRandomSet();

        List<List<String>> powerSet = new PowerSet<String>().iterativePowerSetByLoopOverNumbers(set);

        MatcherAssert.assertThat(powerSet, IsCollectionWithSize.hasSize((1 << set.size())));
        Map<String, Integer> counter = new HashMap<>();
        for (List<String> subset : powerSet) {
            for (String name : subset) {
                int num = counter.getOrDefault(name, 0);
                counter.put(name, num + 1);
            }
        }
        counter.forEach((k, v) -> Assertions.assertEquals((1 << (set.size() - 1)), v.intValue()));
    }

    @Test
    public void givenSet_WhenPowerSetIsCalculatedIterativePowerSetByLoopOverNumbersMinimalChange_ThenItContainsAllSubsets() {

        Set<String> set = RandomSetOfStringGenerator.generateRandomSet();
        List<List<String>> powerSet = new PowerSet<String>().iterativePowerSetByLoopOverNumbersMinimalChange(set);

        MatcherAssert.assertThat(powerSet, IsCollectionWithSize.hasSize((1 << set.size())));
        Map<String, Integer> counter = new HashMap<>();
        for (List<String> subset : powerSet) {
            for (String name : subset) {
                int num = counter.getOrDefault(name, 0);
                counter.put(name, num + 1);
            }
        }
        counter.forEach((k, v) -> Assertions.assertEquals((1 << (set.size() - 1)), v.intValue()));
        for(int i=1; i<powerSet.size(); i++) {
            int diff = 0;
            for (String s : powerSet.get(i - 1))
                if (!powerSet.get(i).contains(s))
                    diff++;
            for (String s : powerSet.get(i))
                if (!powerSet.get(i - 1).contains(s))
                    diff++;
            Assertions.assertEquals(1, diff);
        }
    }


    static class RandomSetOfStringGenerator {
        private static List<String> fruits = Arrays.asList("Apples", "Avocados", "Banana", "Blueberry", "Cherry", "Clementine", "Cucumber", "Date", "Fig",
          "Grapefruit", "Grape", "Kiwi", "Lemon", "Mango", "Mulberry", "Melon", "Nectarine", "Olive", "Orange");

        static Set<String> generateRandomSet() {
            Set<String> set = new HashSet<>();
            Random random = new Random();
            int size = random.nextInt(fruits.size() - 1) + 1;
            while (set.size() != size) {
                set.add(fruits.get(random.nextInt(fruits.size())));
            }
            return set;
        }
    }
}
