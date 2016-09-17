package com.baeldung;

import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class RandomListElementTest {

    @Test
    public void givenList_whenRandomNumberChosen_shouldReturnARandomElementUsingRandom() {
        List<Integer> givenList = Arrays.asList(1, 2, 3);
        Random rand = new Random();
        givenList.get(rand.nextInt(givenList.size()));
    }

    @Test
    public void givenList_whenRandomNumberChosen_shouldReturnARandomElementUsingMathRandom() {
        List<Integer> givenList = Arrays.asList(1, 2, 3);
        givenList.get((int)Math.random() * (givenList.size() - 1));
    }

    @Test
    public void givenList_whenNumberElementsChoesem_shouldReturnRandomElementsWithReapeating() {
        int numberOfElements = 2;
        Random rand = new Random();
        List<String> givenList = Arrays.asList("one", "two", "three", "four");
        for (int i = 0; i < numberOfElements; i++) {
            int randomIndex = rand.nextInt(givenList.size());
            givenList.get(randomIndex);
        }
    }

    @Test
    public void givenList_whenNumberElementsChoesem_shouldReturnRandomElementsWithoutReapeating() {
        int numberOfElements = 2;
        Random rand = new Random();
        List<String> givenList = Arrays.asList("one", "two", "three", "four");
        for (int i = 0; i < numberOfElements; i++) {
            int randomIndex = rand.nextInt(givenList.size());
            givenList.get(randomIndex);
            givenList.remove(randomIndex);
        }
    }

    @Test
    public void givenList_whenSeriesLengthChosen_shouldReturnRandomSeries() {
        List<Integer> givenList = Arrays.asList(1, 2, 3, 4, 5, 6);
        int randomSeriesLength = 3;
        Collections.shuffle(givenList);
        givenList.subList(0, randomSeriesLength - 1);
    }

    @Test
    public void givenList_whenSeriesLengthChosen_shouldReturnOrderedSeries() {
        List<Integer> givenList = Arrays.asList(1, 2, 3, 4, 5, 6);
        Random rand = new Random();
        int startIndex = rand.nextInt(givenList.size());
        int endIndex = startIndex + rand.nextInt(givenList.size() - startIndex);
        Collections.sort(givenList);
        givenList.subList(startIndex, endIndex);
    }

}
