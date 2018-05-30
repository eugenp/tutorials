package com.baeldung.pairs;

import io.vavr.Tuple2;
import org.junit.Assert;
import org.junit.Test;

public class VavrPairsUnitTest {
    @Test
    public void givenTuple_whenSetValue_shouldSucceed() {
        String key = "Eleven";
        double value = 11.0;
        double newValue = 11.1;

        Tuple2<String, Double> pair = new Tuple2<>(key, value);

        pair = pair.update2(newValue);
        Assert.assertTrue(newValue == pair._2());
    }

    @Test
    public void givenPair_whenGetValue_shouldSucceed() {
        String key = "Twelve";
        double value = 12.0;

        Tuple2<String, Double> pair = new Tuple2<>(key, value);

        Assert.assertTrue(value == pair._2());
    }
}