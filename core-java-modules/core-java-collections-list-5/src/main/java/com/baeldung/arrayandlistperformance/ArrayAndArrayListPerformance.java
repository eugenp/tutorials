package com.baeldung.arrayandlistperformance;

import java.util.ArrayList;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.

public class ArrayAndArrayListPerformance {
    public void creation(){
        long startTime = System.nanoTime();
        int[] array = new int[1000000];
        long endTime = System.nanoTime();
        long duration = endTime - startTime;
        System.out.println("Array creation time: " + duration + " nanoseconds");
        long startTime2 = System.nanoTime();
        ArrayList<Integer> list = new ArrayList<>(1000000);
        long endTime2 = System.nanoTime();
        long duration2 = endTime2 - startTime2;
        System.out.println("ArrayList creation time: " + duration2 + " nanoseconds");
    }
    public void setting(){
        int[] array = new int[1000000];
        long startTime = System.nanoTime();
        array[0] = 10;
        long endTime = System.nanoTime();
        long duration = endTime - startTime;
        System.out.println("Array item setting time: " + duration + " nanoseconds");
        ArrayList<Integer> list = new ArrayList<>(1000000);
        long startTime2 = System.nanoTime();
        list.add(0, 10);
        long endTime2 = System.nanoTime();
        long duration2 = endTime2 - startTime2;
        System.out.println("ArrayList item setting time: " + duration2 + " nanoseconds");
    }

    public void getting(){
        int[] array = new int[1000000];
        array[0] = 10;
        long startTime = System.nanoTime();
        int item = array[0];
        long endTime = System.nanoTime();
        long duration = endTime - startTime;
        System.out.println("Array item retrieval time: " + duration + " nanoseconds");
        ArrayList<Integer> list = new ArrayList<>(1000000);
        list.add(0, 10);
        long startTime2 = System.nanoTime();
        int item2 = list.get(0);
        long endTime2 = System.nanoTime();
        long duration2 = endTime2 - startTime2;
        System.out.println("ArrayList item retrieval time: " + duration2 + " nanoseconds");
    }
    public void cloning(){
        int[] array = new int[1000000];
        long startTime = System.nanoTime();
        int[] newArray = array.clone();
        long endTime = System.nanoTime();
        long duration = endTime - startTime;
        System.out.println("Array cloning time: " + duration + " nanoseconds");
        ArrayList<Integer> list = new ArrayList<>(1000000);
        long startTime2 = System.nanoTime();
        ArrayList<Integer> newList = new ArrayList<>(list);
        long endTime2 = System.nanoTime();
        long duration2 = endTime2 - startTime2;
        System.out.println("ArrayList cloning time: " + duration2 + " nanoseconds");
    }

}