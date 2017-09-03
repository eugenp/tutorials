package com.baeldung.pairs;

import javafx.util.Pair;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by banks on 003 Sep 03 2017 2:19 PM.
 */
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