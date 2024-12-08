package com.baeldung.hillsandvalleys;

public class HillValleyCounter {

    public int countHillsAndValleys(int[] numbers) {

        int hills = 0;
        int valleys = 0;
        for (int i = 1; i < numbers.length - 1; i++) {

            int prev = numbers[i - 1];
            int current = numbers[i];
            int next = numbers[i + 1];

            while (i < numbers.length - 1 && numbers[i] == numbers[i + 1]) {
                i++;
            }

            if (i != numbers.length - 1) {
                next = numbers[i + 1];
            }

            if (current > prev && current > next) {
                hills++;
            } else if (current < prev && current < next) {
                valleys++;
            }
        }

        return hills + valleys;
    }
}
