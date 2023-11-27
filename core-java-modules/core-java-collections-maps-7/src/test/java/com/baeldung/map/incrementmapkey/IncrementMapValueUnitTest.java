package com.baeldung.map.incrementmapkey;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.Assert;
import org.junit.Test;

import com.baeldung.map.incrementmapkey.IncrementMapValueWays;

public class IncrementMapValueUnitTest {

    @Test
    public void givenString_whenUsingContainsKey_thenReturnFreqMap() {
        String string = "the quick brown fox jumps over the lazy dog";
        IncrementMapValueWays ic = new IncrementMapValueWays();
        Map<Character, Integer> actualMap = ic.charFrequencyUsingContainsKey(string);
        Map<Character, Integer> expectedMap = getExpectedMap();
        Assert.assertEquals(expectedMap, actualMap);
    }

    @Test
    public void givenString_whenUsingGetOrDefault_thenReturnFreqMap() {
        String string = "the quick brown fox jumps over the lazy dog";
        IncrementMapValueWays ic = new IncrementMapValueWays();
        Map<Character, Integer> actualMap = ic.charFrequencyUsingGetOrDefault(string);
        Map<Character, Integer> expectedMap = getExpectedMap();
        Assert.assertEquals(expectedMap, actualMap);
    }

    @Test
    public void givenString_whenUsingMapMerge_thenReturnFreqMap() {
        String string = "the quick brown fox jumps over the lazy dog";
        IncrementMapValueWays ic = new IncrementMapValueWays();
        Map<Character, Integer> actualMap = ic.charFrequencyUsingMerge(string);
        Map<Character, Integer> expectedMap = getExpectedMap();
        Assert.assertEquals(expectedMap, actualMap);
    }

    @Test
    public void givenString_whenUsingMapCompute_thenReturnFreqMap() {
        String string = "the quick brown fox jumps over the lazy dog";
        IncrementMapValueWays ic = new IncrementMapValueWays();
        Map<Character, Integer> actualMap = ic.charFrequencyUsingCompute(string);
        Map<Character, Integer> expectedMap = getExpectedMap();
        Assert.assertEquals(expectedMap, actualMap);
    }

    @Test
    public void givenString_whenUsingGuava_thenReturnFreqMap() {
        String string = "the quick brown fox jumps over the lazy dog";
        IncrementMapValueWays ic = new IncrementMapValueWays();
        Map<Character, Long> actualMap = ic.charFrequencyUsingAtomicMap(string);
        Map<Character, Integer> expectedMap = getExpectedMap();
        Assert.assertEquals(expectedMap.keySet(), actualMap.keySet());
    }

    @Test
    public void givenString_whenUsingIncrementAndGet_thenReturnFreqMap() {
        String string = "the quick brown fox jumps over the lazy dog";
        IncrementMapValueWays ic = new IncrementMapValueWays();
        Map<Character, AtomicInteger> actualMap = ic.charFrequencyWithGetAndIncrement(string);
        Assert.assertEquals(getExpectedMap().keySet(), actualMap.keySet());
    }

    @Test
    public void givenString_whenUsingIncrementAndGetAndComputeIfAbsent_thenReturnFreqMap() {
        String string = "the quick brown fox jumps over the lazy dog";
        IncrementMapValueWays ic = new IncrementMapValueWays();
        Map<Character, AtomicInteger> actualMap = ic.charFrequencyWithGetAndIncrementComputeIfAbsent(string);
        Assert.assertEquals(getExpectedMap().keySet(), actualMap.keySet());
    }

    @Test
    public void givenString_whenUsingConcurrentMapCompute_thenReturnFreqMap() throws InterruptedException {
        Map<Character, Integer> charMap = new ConcurrentHashMap<>();
        Thread thread1 = new Thread(() -> {
            IncrementMapValueWays ic = new IncrementMapValueWays();
            ic.charFrequencyWithConcurrentMap("the quick brown", charMap);
        });

        Thread thread2 = new Thread(() -> {
            IncrementMapValueWays ic = new IncrementMapValueWays();
            ic.charFrequencyWithConcurrentMap(" fox jumps over the lazy dog", charMap);
        });

        thread1.start();
        thread2.start();
        thread1.join();
        thread2.join();

        Map<Character, Integer> expectedMap = getExpectedMap();
        Assert.assertEquals(expectedMap, charMap);
    }

    private Map<Character, Integer> getExpectedMap() {
        return Stream.of(
            new Object[][] { { ' ', 8 }, { 'a', 1 }, { 'b', 1 }, { 'c', 1 }, { 'd', 1 }, { 'e', 3 }, { 'f', 1 }, { 'g', 1 }, { 'h', 2 }, { 'i', 1 }, { 'j', 1 }, { 'k', 1 }, { 'l', 1 }, { 'm', 1 }, { 'n', 1 }, { 'o', 4 }, { 'p', 1 }, { 'q', 1 }, { 'r', 2 },
              { 's', 1 }, { 't', 2 }, { 'u', 2 }, { 'v', 1 }, { 'w', 1 }, { 'x', 1 }, { 'y', 1 }, { 'z', 1 } })
          .collect(Collectors.toMap(data -> (Character) data[0], data -> (Integer) data[1]));
    }
}
