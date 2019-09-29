package com.baeldung.bucketsort;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

public class IntegerBucketSorter implements Sorter<Integer> {

    private final Comparator<Integer> comparator;

    public IntegerBucketSorter(Comparator<Integer> comparator) {
        this.comparator = comparator;
    }

    public IntegerBucketSorter() {
        comparator = Comparator.naturalOrder();
    }

    public List<Integer> sort(List<Integer> arrayToSort) {

        List<List<Integer>> buckets = splitIntoUnsortedBuckets(arrayToSort);

        for(List<Integer> bucket  : buckets){
            bucket.sort(comparator);
        }

        return concatenateSortedBuckets(buckets);
    }

    private List<Integer> concatenateSortedBuckets(List<List<Integer>> buckets){
        List<Integer> sortedArray = new LinkedList<>();
        for(List<Integer> bucket : buckets){
            sortedArray.addAll(bucket);
        }
        return sortedArray;
    }

    private List<List<Integer>> splitIntoUnsortedBuckets(List<Integer> initialList){

        final int max = findMax(initialList);
        final int numberOfBuckets = (int) Math.sqrt(initialList.size());

        List<List<Integer>> buckets = new ArrayList<>();
        for(int i = 0; i < numberOfBuckets; i++) buckets.add(new ArrayList<>());

        //distribute the data
        for (int i : initialList) {
            buckets.get(hash(i, max, numberOfBuckets)).add(i);
        }
        return buckets;

    }

    private int findMax(List<Integer> input){
        int m = Integer.MIN_VALUE;
        for (int i : input){
            m = Math.max(i, m);
        }
        return m;
    }

    private static int hash(int i, int max, int numberOfBuckets) {
        return (int) ((double) i / max * (numberOfBuckets - 1));
    }


}
