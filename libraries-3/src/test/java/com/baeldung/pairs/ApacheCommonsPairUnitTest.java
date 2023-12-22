package com.baeldung.pairs;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.MutablePair;
import org.junit.Assert;
import org.junit.Test;

public class ApacheCommonsPairUnitTest {

    @Test
    public void givenMutablePair_whenGetValue_shouldPass() {
        int key = 5;
        String value = "Five";

        MutablePair<Integer, String> mutablePair = new MutablePair<>(key, value);
        Assert.assertTrue(mutablePair.getKey() == key);
        Assert.assertEquals(mutablePair.getValue(), value);
    }

    @Test
    public void givenMutablePair_whenSetValue_shouldPass() {
        int key = 6;
        String value = "Six";
        String newValue = "New Six";

        MutablePair<Integer, String> mutablePair = new MutablePair<>(key, value);
        Assert.assertTrue(mutablePair.getKey() == key);
        Assert.assertEquals(mutablePair.getValue(), value);
        mutablePair.setValue(newValue);
        Assert.assertEquals(mutablePair.getValue(), newValue);
    }

    @Test
    public void givenImmutablePair_whenGetValue_shouldPass() {
        int key = 2;
        String value = "Two";

        ImmutablePair<Integer, String> immutablePair = new ImmutablePair<>(key, value);
        Assert.assertTrue(immutablePair.getKey() == key);
        Assert.assertEquals(immutablePair.getValue(), value);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void givenImmutablePair_whenSetValue_shouldFail() {
        ImmutablePair<Integer, String> immutablePair = new ImmutablePair<>(1, "One");
        immutablePair.setValue("Another One");
    }

}
