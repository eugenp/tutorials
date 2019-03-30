package com.baeldung.algorithms.twopointertechnique;

public class TwoSum {

    public boolean twoSum(int[] input, int targetValue) {

        int pointerOne = 0;
        int pointerTwo = input.length - 1;

        while (pointerOne < pointerTwo) {
            int sum = input[pointerOne] + input[pointerTwo];

            if (sum == targetValue) {
                return true;
            } else if (sum < targetValue) {
                pointerOne++;
            } else {
                pointerTwo--;
            }
        }

        return false;
    }

    public boolean twoSumSlow(int[] input, int targetValue) {

        for (int i = 0; i < input.length; i++) {
            for (int j = 1; j < input.length; j++) {
                if (input[i] + input[j] == targetValue) {
                    return true;
                }
            }
        }

        return false;
    }

}
