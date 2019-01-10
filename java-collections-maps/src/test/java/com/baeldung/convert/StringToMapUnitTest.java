package com.baeldung.convert;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

import java.util.Map;

public class StringToMapUnitTest {

    @Test
    public void givenStringForConversion_MapIsCorrectlyBuild_WithStream() {
        Map<String, String> wordsByKey = StringToMap.convertWithStream("1=one,2=two,3=three,4=four");
        Assert.assertEquals(4, wordsByKey.size());
        Assert.assertEquals("one", wordsByKey.get("1"));
    }

    @Test
    void givenStringForConversion_MapIsCorrectlyBuild_WithGuava() {
        Map<String, String> wordsByKey = StringToMap.convertWithGuava("1=one,2=two,3=three,4=four");
        Assert.assertEquals(4, wordsByKey.size());
        Assert.assertEquals("one", wordsByKey.get("1"));
    }
}