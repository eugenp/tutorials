package com.baeldung.stateless;

public enum QuickSort implements SortingStrategy {
    
    INSTANCE;

    @Override
    public void sort(int[] array) {
        quickSort(array, 0, array.length - 1);
    }
    
    private void quickSort(int[] array, int begin, int end) {
        if (begin < end) {
            int pi = partition(array, begin, end);
            quickSort(array, begin, pi - 1);
            quickSort(array, pi + 1, end);
        }
    }    

    private int partition(int[] array, int low, int high) {
        int pivot = array[high];
        int i = low - 1;
        for (int j = low; j < high; j++) {
            if (array[j] < pivot) {
                i++;
                int swap = array[i];
                array[i] = array[j];
                array[j] = swap;
            }
        }
        int swap = array[i + 1];
        array[i + 1] = array[high];
        array[high] = swap;
        return i + 1;
    }
}
