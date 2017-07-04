package com.baeldung.algorithms.minimax;

import java.util.ArrayList;
import java.util.List;

public class GameOfBones {
    public static List<Integer> getPossibleStates(int noOfBonesInHeap) {
        List<Integer> listOfPossibleHeaps = new ArrayList<>();
        for (int i = 1; i <= 3; i++) {
            int newHeapCount = noOfBonesInHeap - i;
            if (newHeapCount >= 0) {
                listOfPossibleHeaps.add(newHeapCount);
            }
        }
        return listOfPossibleHeaps;
    }
}
