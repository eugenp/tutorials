package com.baeldung.stateless;

import java.util.List;

public class QuickSort implements SortingStrategy {

    @Override
    public List<Integer> sort(List<Integer> list) {
        quickSort(list, 0, list.size()-1);
        return list;
    }
    
    private void quickSort(List<Integer> list, int begin, int end) {
        if (begin < end) {
            int pi = partition(list, begin, end);
            quickSort(list, begin, pi-1);
            quickSort(list, pi+1, end);
        }
    }
    
    private int partition(List<Integer> list, int begin, int end) {
        int pivot = list.get(end);
        int i = begin - 1;
        for (int j = begin; j < end; j++) {
            if (list.get(j) < pivot) {
                i++;
                int swap = list.get(i);
                list.set(i, list.get(j));
                list.set(j, swap);
            }
        }
        int swap = list.get(i+1);
        list.set(i+1, list.get(end));
        list.set(end, swap);
        return i+1;
    }
}
