package com.baeldung.signchanges;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.stream.IntStream;

public class SignChangesInAnArrayUnitTest {

    int[] sampleArray = {1, -2, -3, 4, 0, -1, 5};

    int countSignChanges(int[] arr) {
        if (arr == null || arr.length < 2) {
            return 0;
        }
        int count = 0;

        int prevSign = Integer.signum(arr[0]);

        for (int i = 1; i < arr.length; i++) {
            int currentSign = Integer.signum(arr[i]);

            if (currentSign != 0 && prevSign != 0 && currentSign != prevSign) {
                count++;
            }

            if (currentSign != 0) {
                prevSign = currentSign;
            }
        }

        return count;
    }

    @Test
    void givenArray_whenExistsSignChanges_thenReturnSignChangesQuantity() {
        int result = countSignChanges(sampleArray);
        Assertions.assertThat(result).isEqualTo(4);
    }

    int countSignChangesStream(int[] arr) {
        if (arr == null || arr.length < 2) {
            return 0;
        }

        int[] signs = Arrays.stream(arr)
            .map(Integer::signum)
            .filter(s -> s != 0)
            .toArray();

        return (int) IntStream.range(1, signs.length)
            .filter(i -> signs[i] != signs[i - 1])
            .count();
    }

    @Test
    void givenArray_whenUsingStreams_thenReturnSignChangesQuantity() {
        int result = countSignChangesStream(sampleArray);
        Assertions.assertThat(result).isEqualTo(4);
    }

}
