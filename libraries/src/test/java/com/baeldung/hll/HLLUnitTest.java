package com.baeldung.hll;


import com.google.common.hash.HashFunction;
import com.google.common.hash.Hashing;
import net.agkn.hll.HLL;
import org.junit.Test;

import java.util.stream.LongStream;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

public class HLLUnitTest {

    @Test
    public void givenHLL_whenAddHugeAmountOfNumbers_thenShouldReturnEstimatedCardinality() {
        //given
        int numberOfElements = 100_000_000;
        int toleratedDifference = 1_000_000;
        HashFunction hashFunction = Hashing.murmur3_128();
        HLL hll = new HLL(14, 5);

        //when
        LongStream.range(0, numberOfElements).forEach(element -> {
                    long hashedValue = hashFunction.newHasher().putLong(element).hash().asLong();
                    hll.addRaw(hashedValue);
                }
        );

        //then
        long cardinality = hll.cardinality();
        assertThat(isSimilarTo(cardinality, numberOfElements, toleratedDifference)).isTrue();
    }

    @Test
    public void givenTwoHLLs_whenAddHugeAmountOfNumbers_thenShouldReturnEstimatedCardinalityForUnionOfHLLs() {
        //given
        int numberOfElements = 100_000_000;
        int toleratedDifference = 1_000_000;
        HashFunction hashFunction = Hashing.murmur3_128();
        HLL firstHll = new HLL(15, 5);
        HLL secondHLL = new HLL(15, 5);

        //when
        LongStream.range(0, numberOfElements).forEach(element -> {
                    long hashedValue = hashFunction.newHasher().putLong(element).hash().asLong();
                    firstHll.addRaw(hashedValue);
                }
        );

        LongStream.range(numberOfElements, numberOfElements * 2).forEach(element -> {
                    long hashedValue = hashFunction.newHasher().putLong(element).hash().asLong();
                    secondHLL.addRaw(hashedValue);
                }
        );

        //then
        firstHll.union(secondHLL);
        long cardinality = firstHll.cardinality();
        assertThat(isSimilarTo(cardinality, numberOfElements * 2, toleratedDifference)).isTrue();
    }

    private boolean isSimilarTo(long cardinality, int numberOfElements, int maxToleratedDifference) {
        System.out.println(cardinality);
        return Math.abs(cardinality - numberOfElements) <= maxToleratedDifference;
    }
}
