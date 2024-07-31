package com.baeldung.equilibriumindex;

import java.util.ArrayList;
import java.util.List;

class EquilibriumIndexFinder {
    
    List<Integer> findEquilibriumIndexes(int[] array) {
        int[] partialSums = new int[array.length + 1];
        partialSums[0] = 0;
        for (int i = 0; i < array.length; i++) {
            partialSums[i+1] = partialSums[i] + array[i]; 
        }
        
        List<Integer> equilibriumIndexes = new ArrayList<Integer>();
        for (int i = 0; i < array.length; i++) {
            if (partialSums[i] == (partialSums[array.length] - (partialSums[i+1]))) {
                equilibriumIndexes.add(i);
            }
        }
        return equilibriumIndexes;
    }

}
