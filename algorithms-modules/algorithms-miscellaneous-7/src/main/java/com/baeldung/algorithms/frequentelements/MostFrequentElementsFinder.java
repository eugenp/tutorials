package com.baeldung.algorithms.frequentelements;

import java.util.*;
import java.util.stream.Collectors;

public class MostFrequentElementsFinder {

    public static List<Integer> findByHashMapAndPriorityQueue(Integer[] array, int n) {
        Map<Integer, Integer> countMap = new HashMap<>();

        // For each element i in the array, add it to the countMap and increment its count.
        for (Integer i : array) {
            countMap.put(i, countMap.getOrDefault(i, 0) + 1);
        }

        // Create a max heap (priority queue) that will prioritize elements with higher counts.
        PriorityQueue<Integer> heap = new PriorityQueue<>(
                (a, b) -> countMap.get(b) - countMap.get(a));

        // Add all the unique elements in the array to the heap.
        heap.addAll(countMap.keySet());

        List<Integer> result = new ArrayList<>();
        for (int i = 0; i < n && !heap.isEmpty(); i++) {
            // Poll the highest-count element from the heap and add it to the result list.
            result.add(heap.poll());
        }

        return result;
    }

    public static List<Integer> findByStream(Integer[] arr, int n) {
        return Arrays.stream(arr).collect(Collectors.groupingBy(i -> i, Collectors.counting()))
                .entrySet().stream()
                .sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
                .map(Map.Entry::getKey)
                .limit(n)
                .collect(Collectors.toList());
    }

    public static List<Integer> findByTreeMap(Integer[] arr, int n) {
        // Create a TreeMap and use a reverse order comparator to sort the entries by frequency in descending order
        Map<Integer, Integer> countMap = new TreeMap<>(Collections.reverseOrder());

        for (int i : arr) {
            countMap.put(i, countMap.getOrDefault(i, 0) + 1);
        }

        // Create a list of the map entries and sort them by value (i.e. by frequency) in descending order
        List<Map.Entry<Integer, Integer>> sortedEntries = new ArrayList<>(countMap.entrySet());
        sortedEntries.sort((e1, e2) -> e2.getValue().compareTo(e1.getValue()));

        // Extract the n most frequent elements from the sorted list of entries
        List<Integer> result = new ArrayList<>();
        for (int i = 0; i < n && i < sortedEntries.size(); i++) {
            result.add(sortedEntries.get(i).getKey());
        }

        return result;
    }

    public static List<Integer> findByBucketSort(Integer[] arr, int n) {
        List<Integer> result = new ArrayList<>();
        Map<Integer, Integer> freqMap = new HashMap<>();
        List<Integer>[] bucket = new List[arr.length + 1];

        // Loop through the input array and update the frequency count of each element in the HashMap
        for (int num : arr) {
            freqMap.put(num, freqMap.getOrDefault(num, 0) + 1);
        }

        // Loop through the HashMap and add each element to its corresponding bucket based on its frequency count
        for (int num : freqMap.keySet()) {
            int freq = freqMap.get(num);
            if (bucket[freq] == null) {
                bucket[freq] = new ArrayList<>();
            }
            bucket[freq].add(num);
        }

        // Loop through the bucket array in reverse order and add the elements to the result list
        // until we have found the n most frequent elements
        for (int i = bucket.length - 1; i >= 0 && result.size() < n; i--) {
            if (bucket[i] != null) {
                result.addAll(bucket[i]);
            }
        }

        // Return a sublist of the result list containing only the first n elements
        return result.subList(0, n);
    }

}
