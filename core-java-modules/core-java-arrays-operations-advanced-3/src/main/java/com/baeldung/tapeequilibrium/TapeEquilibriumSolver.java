package com.baeldung.tapeequilibrium;

class TapeEquilibriumSolver {

    int calculateTapeEquilibrium(int[] array) {
        int[] partialSums = new int[array.length];
        partialSums[0] = array[0];
        for (int i=1; i<array.length; i++) {
            partialSums[i] = partialSums[i-1] + array[i];
        }
        
        int minimalDifference = absoluteDifferenceAtIndex(partialSums, 0);
        for (int i=1; i<array.length - 1; i++) {
            minimalDifference = Math.min(minimalDifference, absoluteDifferenceAtIndex(partialSums, i));
        }
        return minimalDifference;
    }
    
    int absoluteDifferenceAtIndex(int[] partialSums, int index) {
        return Math.abs((partialSums[partialSums.length - 1] - partialSums[index]) - partialSums[index]);
    }
    
}
