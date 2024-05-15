package com.baeldung.map.convert;

import org.apache.commons.collections4.MapUtils;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

public class MapToStringUnitTest {

    private Map<Integer, String> wordsByKey = new HashMap<>();

    @BeforeEach
    public void setup() {
        wordsByKey.clear();
        wordsByKey.put(1, "one");
        wordsByKey.put(2, "two");
        wordsByKey.put(3, "three");
        wordsByKey.put(4, "four");
    }

    @Test
    public void givenMap_WhenUsingIteration_ThenResultingMapIsCorrect() {
        String mapAsString = MapToString.convertWithIteration(wordsByKey);
        Assert.assertEquals("{1=one, 2=two, 3=three, 4=four}", mapAsString);
    }

    @Test
    public void givenMap_WhenUsingStream_ThenResultingMapIsCorrect() {
        String mapAsString = MapToString.convertWithStream(wordsByKey);
        Assert.assertEquals("{1=one, 2=two, 3=three, 4=four}", mapAsString);
    }

    @Test
    public void givenMap_WhenUsingGuava_ThenResultingMapIsCorrect() {
        String mapAsString = MapToString.convertWithGuava(wordsByKey);
        Assert.assertEquals("1=one,2=two,3=three,4=four", mapAsString);
    }

    @Test
    public void givenMap_WhenUsingApache_ThenResultingMapIsCorrect() {
        String mapAsString = MapToString.convertWithApache(wordsByKey);
        Assert.assertEquals("{1=one, 2=two, 3=three, 4=four}", mapAsString);
        MapUtils.debugPrint(System.out, "Map as String", wordsByKey);
    }
}