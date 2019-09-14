package com.baeldung.bucketsort;

import java.util.ArrayList;
import java.util.Comparator;
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
        List<Integer> sortedArray = new ArrayList<>();
        int index = 0;
        for(List<Integer> bucket : buckets){
            for(int number : bucket){
                sortedArray.add(index++, number);
            }
        }
        return sortedArray;
    }

    private List<List<Integer>> splitIntoUnsortedBuckets(List<Integer> initialList){

        final int[] codes = createHashes(initialList);

        List<List<Integer>> buckets = new ArrayList<>(codes[1]);
        for(int i = 0; i < codes[1]; i++) buckets.add(new ArrayList<>());

        //distribute the data
        for (int i : initialList) {
            buckets.get(hash(i, codes)).add(i);
        }
        return buckets;

    }

    private int[] createHashes(List<Integer> input){
        int m = input.get(0);
        for (int i = 1; i < input.size(); i++) {
            if (m < input.get(i)) {
                m = input.get(i);
            }
        }
        return new int[]{m, (int) Math.sqrt(input.size())};
    }

    private static int hash(int i, int[] code) {
        return (int) ((double) i / code[0] * (code[1] - 1));
    }

}
