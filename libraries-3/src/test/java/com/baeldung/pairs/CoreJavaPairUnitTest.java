package com.baeldung.pairs;

import org.junit.Assert;
import org.junit.Test;

import javafx.util.Pair;

public class CoreJavaPairUnitTest {
    @Test
    public void givenPair_whenGetValue_shouldSucceed() {
        String key = "Good Day";
        boolean value = true;
        Pair<String, Boolean> pair = new Pair<>(key, value);

        Assert.assertEquals(key, pair.getKey());
        Assert.assertEquals(value, pair.getValue());
    }
}