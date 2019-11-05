package com.baeldung.algorithms.minimax;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

class GameOfBones {
    static List<Integer> getPossibleStates(int noOfBonesInHeap) {
        return IntStream.rangeClosed(1, 3).boxed()
          .map(i -> noOfBonesInHeap - i)
          .filter(newHeapCount -> newHeapCount >= 0)
          .collect(Collectors.toList());
    }
}
