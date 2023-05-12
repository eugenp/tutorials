package com.baeldung.arrayandlistperformance;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

public class PerformanceUnitTest {
    @Test
    public void givenArrayAndArrayList_whenComparingCreationTime_thenVerifyPerformanceDifference(){
        long startTime = System.nanoTime();
        int[] array = new int[1000000];
        long endTime = System.nanoTime();

        long duration = endTime - startTime;
        long startTime2 = System.nanoTime();
        ArrayList<Integer> list = new ArrayList<>(1000000);
        long endTime2 = System.nanoTime();

        long duration2 = endTime2 - startTime2;
        if (duration < duration2) {
            Assertions.assertTrue(duration < duration2, "Array creation is faster than Array creation");
        }else if (duration > duration2) {
            Assertions.assertTrue(duration > duration2, "ArrayList creation is faster than Array creation");
        }else {
            Assertions.assertEquals(duration, duration2,"ArrayList creation is equal to Array creation");
        }
    }
    @Test
    public void givenArrayAndArrayList_whenComparingSettingTime_thenVerifyPerformanceDifference(){
        int[] array = new int[1000000];

        long startTime = System.nanoTime();
        array[0] = 10;
        long endTime = System.nanoTime();

        long duration = endTime - startTime;

        ArrayList<Integer> list = new ArrayList<>(1000000);

        long startTime2 = System.nanoTime();
        list.add(0, 10);
        long endTime2 = System.nanoTime();

        long duration2 = endTime - startTime;
        if (duration < duration2) {
            Assertions.assertTrue(duration < duration2, "Array setting is faster than Array setting");
        }else if (duration > duration2) {
            Assertions.assertTrue(duration > duration2, "ArrayList setting is faster than Array setting");
        }else {
            Assertions.assertEquals(duration, duration2,"ArrayList setting is equal to Array setting");
        }
    }
    @Test
    public void givenArrayAndArrayList_whenComparingGettingTime_thenVerifyPerformanceDifference(){
        int[] array = new int[1000000];
        array[0] = 10;

        long startTime = System.nanoTime();
        int item = array[0];
        long endTime = System.nanoTime();

        long duration = endTime - startTime;
        ArrayList<Integer> list = new ArrayList<>(1000000);
        list.add(0, 10);

        long startTime2 = System.nanoTime();
        int item2 = list.get(0);
        long endTime2 = System.nanoTime();

        long duration2 = endTime2 - startTime2;
        if (duration < duration2) {
            Assertions.assertTrue(duration < duration2, "Array getting is faster than Array getting");
        }else if (duration > duration2) {
            Assertions.assertTrue(duration > duration2, "ArrayList getting is faster than Array getting");
        }else {
            Assertions.assertEquals(duration, duration2,"ArrayList getting is equal to Array getting");
        }    }
    @Test
    public void givenArrayAndArrayList_whenComparingCloningTime_thenVerifyPerformanceDifference(){
        int[] array = new int[1000000];

        long startTime = System.nanoTime();
        int[] newArray = array.clone();
        long endTime = System.nanoTime();

        long duration = endTime - startTime;
        ArrayList<Integer> list = new ArrayList<>(1000000);

        long startTime2 = System.nanoTime();
        ArrayList<Integer> newList = new ArrayList<>(list);
        long endTime2 = System.nanoTime();

        long duration2 = endTime2 - startTime2;
        if (duration < duration2) {
            Assertions.assertTrue(duration < duration2, "Array cloning is faster than Array cloning");
        }else if (duration > duration2) {
            Assertions.assertTrue(duration > duration2, "ArrayList cloning is faster than Array cloning");
        }else {
            Assertions.assertEquals(duration, duration2,"ArrayList cloning is equal to Array cloning");
        }
    }
}
