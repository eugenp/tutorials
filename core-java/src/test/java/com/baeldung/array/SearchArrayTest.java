package com.baeldung.array;

import org.junit.Test;

import java.util.*;

public class SearchArrayTest {


    @Test
    public void searchArrayAllocNewCollections() {

        int count = 1000;

        String[] array = seedArray(count);

        long startTime = System.nanoTime();
        for (int i = 0; i < count; i++) {
            searchList(array, "A");
        }
        long duration = System.nanoTime() - startTime;
        System.out.println("SearchList:  " + duration / 10000);

        startTime = System.nanoTime();
        for (int i = 0; i < count; i++) {
            searchSet(array,"A");
        }
        duration = System.nanoTime() - startTime;
        System.out.println("SearchSet:  " + duration / 10000);

        startTime = System.nanoTime();
        for (int i = 0; i < count; i++) {
            searchLoop(array, "A");
        }
        duration = System.nanoTime() - startTime;
        System.out.println("SearchLoop:  " + duration / 10000);
    }

    @Test
    public void searchArrayReuseCollections() {

        int count = 10000;
        String[] array = seedArray(count);

        List<String> asList = Arrays.asList(array);
        Set<String> asSet = new HashSet<>(Arrays.asList(array));

        long startTime = System.nanoTime();
        for (int i = 0; i < count; i++) {
            asList.contains("A");
        }
        long duration = System.nanoTime() - startTime;
        System.out.println("List:  " + duration / 10000);

        startTime = System.nanoTime();
        for (int i = 0; i < count; i++) {
            asSet.contains("A");
        }
        duration = System.nanoTime() - startTime;
        System.out.println("Set:  " + duration / 10000);

        startTime = System.nanoTime();
        for (int i = 0; i < count; i++) {
            searchLoop(array, "A");
        }
        duration = System.nanoTime() - startTime;
        System.out.println("Loop:  " + duration / 10000);

    }


    @Test
    public void searchArrayBinarySearch() {

        int count = 10000;
        String[] array = seedArray(count);
        Arrays.sort(array);

        long startTime = System.nanoTime();
        for (int i = 0; i < count; i++) {
            Arrays.binarySearch(array, "A");
        }
        long duration = System.nanoTime() - startTime;
        System.out.println("Binary search:  " + duration / 10000);

    }

    private boolean searchList(String[] arr, String targetValue) {
      return Arrays.asList(arr).contains(targetValue);
  }

    private boolean searchSet(String[] arr, String targetValue) {
        Set<String> set = new HashSet<>(Arrays.asList(arr));
        return set.contains(targetValue);
    }

    private boolean searchLoop(String[] arr, String targetValue) {
        for (String s : arr) {
            if (s.equals(targetValue))
            return true;
        }
        return true;
    }

    private String[] seedArray(int length) {

        String[] strings = new String[length];
        Random random = new Random();
        for (int i = 0; i < length; i++)
        {
            strings[i] = String.valueOf(random.nextInt());
        }
        return strings;
    }

}
