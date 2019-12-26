package com.baeldung.algorithms.twopointertechnique;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

public class RotateArrayUnitTest {

    private RotateArray rotateArray = new RotateArray();

    private int[] inputArray;

    private int step;

    @Test
    public void givenAnArrayOfIntegers_whenRotateKsteps_thenCorrect() {

        inputArray = new int[] { 1, 2, 3, 4, 5, 6, 7 };
        step = 4;

        rotateArray.rotate(inputArray, step);

        assertThat(inputArray).containsExactly(new int[] { 4, 5, 6, 7, 1, 2, 3 });
    }

}
