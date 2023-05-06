package com.baeldung.stateless;

import java.util.List;

public class BubbleSort implements SortingStrategy {

    @Override
    public List<Integer> sort(List<Integer> list) {
        int n = list.size();
        for (int i = 0; i < n-1; i++) {
            for (int j = 0; j < n-i-1; j++) {
                if (list.get(j) > list.get(j+1)) {
                    int swap = list.get(j);
                    list.set(j, list.get(j + 1));
                    list.set(j + 1, swap);
                }
            }
        }
        return list;
    }
}
