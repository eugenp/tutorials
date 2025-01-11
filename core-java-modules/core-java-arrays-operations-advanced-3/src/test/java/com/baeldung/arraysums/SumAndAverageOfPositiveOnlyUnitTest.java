package com.baeldung.arraysums;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

public class SumAndAverageOfPositiveOnlyUnitTest {

    private static final int[] MY_ARRAY = new int[] { 1, -2, 3, -4, 5, -6, 7, 42, 0 };
    private static final int EXPECTED_SUM = 58; //1+3+5+7+42
    private static final double EXPECTED_AVG = 11.60D; //(1+3+5+7+42)/5

    @Test
    void whenUsingForLoop_thenCorrect() {
        int count = 0;
        int sum = 0;
        for (int value : MY_ARRAY) {
            if (value > 0) {
                count++;
                sum += value;
            }
        }
        double avg = (double) sum / count;
        assertEquals(EXPECTED_SUM, sum);
        assertEquals(EXPECTED_AVG, avg);
    }

    @Test
    void whenUsingStream_thenCorrect() {
        int sum = Arrays.stream(MY_ARRAY).filter(n -> n > 0).sum();
        double avg = Arrays.stream(MY_ARRAY).filter(n -> n > 0).average().orElse(Double.NaN);

        assertEquals(EXPECTED_SUM, sum);
        assertEquals(EXPECTED_AVG, avg);
    }

}
