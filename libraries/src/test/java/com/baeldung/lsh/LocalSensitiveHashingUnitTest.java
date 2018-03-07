package com.baeldung.lsh;

import info.debatty.java.lsh.LSHMinHash;
import org.junit.Ignore;
import org.junit.Test;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

public class LocalSensitiveHashingUnitTest {

    @Ignore("for simplicity of the example number of input vectors is very low, that's why LSH may yield non deterministic results")
    @Test()
    public void givenNVectors_whenPerformLSH_thenShouldCalculateSameHashForSimilarVectors() {
        // given
        boolean[] vector1 = new boolean[] { true, true, true, true, true };
        boolean[] vector2 = new boolean[] { false, false, false, true, false };
        boolean[] vector3 = new boolean[] { false, false, true, true, false };

        int sizeOfVectors = 5;
        int numberOfBuckets = 10;
        int stages = 4;

        LSHMinHash lsh = new LSHMinHash(stages, numberOfBuckets, sizeOfVectors);

        // when
        int[] firstHash = lsh.hash(vector1);
        int[] secondHash = lsh.hash(vector2);
        int[] thirdHash = lsh.hash(vector3);

        System.out.println(Arrays.toString(firstHash));
        System.out.println(Arrays.toString(secondHash));
        System.out.println(Arrays.toString(thirdHash));

        // then
        int lastIndexOfResult = stages - 1;
        assertThat(firstHash[lastIndexOfResult]).isNotEqualTo(secondHash[lastIndexOfResult]);
        assertThat(firstHash[lastIndexOfResult]).isNotEqualTo(thirdHash[lastIndexOfResult]);
        assertThat(isCloseOrEqual(secondHash[lastIndexOfResult], thirdHash[lastIndexOfResult], numberOfBuckets)).isTrue();
    }

    private boolean isCloseOrEqual(int secondHash, int thirdHash, int numberOfBuckets) {
        return Math.abs(secondHash - thirdHash) < numberOfBuckets / 2;
    }
}
