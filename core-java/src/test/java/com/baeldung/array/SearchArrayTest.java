package com.baeldung.array;

import org.junit.Test;

import java.util.*;

public class SearchArrayTest {


    @Test
    public void searchArrayAllocNewCollections() {

        int count = 1000;

        String[] strings = seedArray(count);

        long startTime = System.nanoTime();
        for (int i = 0; i < count; i++) {
            searchList(strings, "W");
        }
        long duration = System.nanoTime() - startTime;
        System.out.println("SearchList:  " + duration / 10000);

        startTime = System.nanoTime();
        for (int i = 0; i < count; i++) {
            searchSet(strings,"S");
        }
        duration = System.nanoTime() - startTime;
        System.out.println("SearchSet:  " + duration / 10000);

        startTime = System.nanoTime();
        for (int i = 0; i < count; i++) {
            searchLoop(strings, "T");
        }
        duration = System.nanoTime() - startTime;
        System.out.println("SearchLoop:  " + duration / 10000);
    }

    @Test
    public void searchArrayReuseCollections() {

        int count = 10000;
        String[] strings = seedArray(count);

        List<String> asList = Arrays.asList(strings);
        Set<String> asSet = new HashSet<>(Arrays.asList(strings));

        long startTime = System.nanoTime();
        for (int i = 0; i < count; i++) {
            asList.contains("W");
        }
        long duration = System.nanoTime() - startTime;
        System.out.println("List:  " + duration / 10000);

        startTime = System.nanoTime();
        for (int i = 0; i < count; i++) {
            asSet.contains("S");
        }
        duration = System.nanoTime() - startTime;
        System.out.println("Set:  " + duration / 10000);

        startTime = System.nanoTime();
        for (int i = 0; i < count; i++) {
            searchLoop(strings, "T");
        }
        duration = System.nanoTime() - startTime;
        System.out.println("Loop:  " + duration / 10000);

    }


    @Test
    public void searchArrayBinarySearch() {

        int count = 10000;
        String[] strings = seedArray(count);
        Arrays.sort(strings);

        long startTime = System.nanoTime();
        for (int i = 0; i < count; i++) {
            Arrays.binarySearch(strings, "A");
        }
        long duration = System.nanoTime() - startTime;
        System.out.println("Binary search:  " + duration / 10000);

    }

    private boolean searchList(String[] strings, String searchString) {
      return Arrays.asList(strings).contains(searchString);
  }

    private boolean searchSet(String[] strings, String searchString) {
        Set<String> set = new HashSet<>(Arrays.asList(strings));
        return set.contains(searchString);
    }

    private boolean searchLoop(String[] strings, String searchString) {
        for (String s : strings) {
            if (s.equals(searchString))
            return true;
        }
        return false;
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
