package com.baeldung.map.compare;

import com.google.common.base.Equivalence;
import com.google.common.collect.MapDifference;
import com.google.common.collect.MapDifference.ValueDifference;
import com.google.common.collect.Maps;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsMapContaining.hasEntry;
import static org.hamcrest.collection.IsMapContaining.hasKey;
import static org.junit.Assert.*;

public class HashMapComparisonUnitTest {
    
    Map<String, String> asiaCapital1;
    Map<String, String> asiaCapital2;
    Map<String, String> asiaCapital3;
    
    Map<String, String[]> asiaCity1;
    Map<String, String[]> asiaCity2;
    Map<String, String[]> asiaCity3;
    
    @Before
    public void setup(){
        asiaCapital1 = new HashMap<String, String>();
        asiaCapital1.put("Japan", "Tokyo");
        asiaCapital1.put("South Korea", "Seoul");
        
        asiaCapital2 = new HashMap<String, String>();
        asiaCapital2.put("South Korea", "Seoul");
        asiaCapital2.put("Japan", "Tokyo");

        asiaCapital3 = new HashMap<String, String>();
        asiaCapital3.put("Japan", "Tokyo");
        asiaCapital3.put("China", "Beijing");
        
        asiaCity1 = new HashMap<String, String[]>();
        asiaCity1.put("Japan", new String[] { "Tokyo", "Osaka" });
        asiaCity1.put("South Korea", new String[] { "Seoul", "Busan" });

        asiaCity2 = new HashMap<String, String[]>();
        asiaCity2.put("South Korea", new String[] { "Seoul", "Busan" });
        asiaCity2.put("Japan", new String[] { "Tokyo", "Osaka" });

        asiaCity3 = new HashMap<String, String[]>();
        asiaCity3.put("Japan", new String[] { "Tokyo", "Osaka" });
        asiaCity3.put("China", new String[] { "Beijing", "Hong Kong" });
    }

    @Test
    public void whenCompareTwoHashMapsUsingEquals_thenSuccess() {
        assertTrue(asiaCapital1.equals(asiaCapital2));
        assertFalse(asiaCapital1.equals(asiaCapital3));
    }

    @Test
    public void whenCompareTwoHashMapsWithArrayValuesUsingEquals_thenFail() {
        assertFalse(asiaCity1.equals(asiaCity2));
    }

    @Test
    public void whenCompareTwoHashMapsUsingStreamAPI_thenSuccess() {
        assertTrue(areEqual(asiaCapital1, asiaCapital2));
        assertFalse(areEqual(asiaCapital1, asiaCapital3));
    }

    @Test
    public void whenCompareTwoHashMapsWithArrayValuesUsingStreamAPI_thenSuccess() {
        assertTrue(areEqualWithArrayValue(asiaCity1, asiaCity2));
        assertFalse(areEqualWithArrayValue(asiaCity1, asiaCity3));
    }

    @Test
    public void whenCompareTwoHashMapKeys_thenSuccess() {
        assertTrue(asiaCapital1.keySet().equals(asiaCapital2.keySet()));
        assertFalse(asiaCapital1.keySet().equals(asiaCapital3.keySet()));
    }

    @Test
    public void whenCompareTwoHashMapKeyValuesUsingStreamAPI_thenSuccess() {
        Map<String, String> asiaCapital3 = new HashMap<String, String>();
        asiaCapital3.put("Japan", "Tokyo");
        asiaCapital3.put("South Korea", "Seoul");
        asiaCapital3.put("China", "Beijing");

        Map<String, String> asiaCapital4 = new HashMap<String, String>();
        asiaCapital4.put("South Korea", "Seoul");
        asiaCapital4.put("Japan", "Osaka");
        asiaCapital4.put("China", "Beijing");

        Map<String, Boolean> result = areEqualKeyValues(asiaCapital3, asiaCapital4);
        
        assertEquals(3, result.size());
        assertThat(result, hasEntry("Japan", false));
        assertThat(result, hasEntry("South Korea", true));
        assertThat(result, hasEntry("China", true));
    }

    @Test
    public void givenDifferentMaps_whenGetDiffUsingGuava_thenSuccess() {
        Map<String, String> asia1 = new HashMap<String, String>();
        asia1.put("Japan", "Tokyo");
        asia1.put("South Korea", "Seoul");
        asia1.put("India", "New Delhi");

        Map<String, String> asia2 = new HashMap<String, String>();
        asia2.put("Japan", "Tokyo");
        asia2.put("China", "Beijing");
        asia2.put("India", "Delhi");

        MapDifference<String, String> diff = Maps.difference(asia1, asia2);
        Map<String, ValueDifference<String>> entriesDiffering = diff.entriesDiffering();
        
        assertFalse(diff.areEqual());
        assertEquals(1, entriesDiffering.size());
        assertThat(entriesDiffering, hasKey("India"));
        assertEquals("New Delhi", entriesDiffering.get("India").leftValue());
        assertEquals("Delhi", entriesDiffering.get("India").rightValue());
    }

    @Test
    public void givenDifferentMaps_whenGetEntriesOnOneSideUsingGuava_thenSuccess() {
        Map<String, String> asia1 = new HashMap<String, String>();
        asia1.put("Japan", "Tokyo");
        asia1.put("South Korea", "Seoul");
        asia1.put("India", "New Delhi");

        Map<String, String> asia2 = new HashMap<String, String>();
        asia2.put("Japan", "Tokyo");
        asia2.put("China", "Beijing");
        asia2.put("India", "Delhi");

        MapDifference<String, String> diff = Maps.difference(asia1, asia2);
        Map<String, String> entriesOnlyOnRight = diff.entriesOnlyOnRight();
        Map<String, String> entriesOnlyOnLeft = diff.entriesOnlyOnLeft();

        assertEquals(1, entriesOnlyOnRight.size());
        assertThat(entriesOnlyOnRight, hasEntry("China", "Beijing"));
        assertEquals(1, entriesOnlyOnLeft.size());
        assertThat(entriesOnlyOnLeft, hasEntry("South Korea", "Seoul"));
    }

    @Test
    public void givenDifferentMaps_whenGetCommonEntriesUsingGuava_thenSuccess() {
        Map<String, String> asia1 = new HashMap<String, String>();
        asia1.put("Japan", "Tokyo");
        asia1.put("South Korea", "Seoul");
        asia1.put("India", "New Delhi");

        Map<String, String> asia2 = new HashMap<String, String>();
        asia2.put("Japan", "Tokyo");
        asia2.put("China", "Beijing");
        asia2.put("India", "Delhi");

        MapDifference<String, String> diff = Maps.difference(asia1, asia2);
        Map<String, String> entriesInCommon = diff.entriesInCommon();

        assertEquals(1, entriesInCommon.size());
        assertThat(entriesInCommon, hasEntry("Japan", "Tokyo"));
    }

    @Test
    public void givenSimilarMapsWithArrayValue_whenCompareUsingGuava_thenFail() {
        MapDifference<String, String[]> diff = Maps.difference(asiaCity1, asiaCity2);
        assertFalse(diff.areEqual());
    }

    @Test
    public void givenSimilarMapsWithArrayValue_whenCompareUsingGuavaEquivalence_thenSuccess() {
        Equivalence<String[]> eq = new Equivalence<String[]>() {
            @Override
            protected boolean doEquivalent(String[] a, String[] b) {
                return Arrays.equals(a, b);
            }

            @Override
            protected int doHash(String[] value) {
                return value.hashCode();
            }
        };

        MapDifference<String, String[]> diff = Maps.difference(asiaCity1, asiaCity2, eq);
        assertTrue(diff.areEqual());
        
        diff = Maps.difference(asiaCity1, asiaCity3, eq);
        assertFalse(diff.areEqual());
    }

    // ===========================================================================

    private boolean areEqual(Map<String, String> first, Map<String, String> second) {
        if (first.size() != second.size()) {
            return false;
        }

        return first.entrySet()
            .stream()
            .allMatch(e -> e.getValue()
                .equals(second.get(e.getKey())));
    }

    private boolean areEqualWithArrayValue(Map<String, String[]> first, Map<String, String[]> second) {
        if (first.size() != second.size()) {
            return false;
        }

        return first.entrySet()
            .stream()
            .allMatch(e -> Arrays.equals(e.getValue(), second.get(e.getKey())));
    }

    private Map<String, Boolean> areEqualKeyValues(Map<String, String> first, Map<String, String> second) {
        return first.entrySet()
            .stream()
            .collect(Collectors.toMap(e -> e.getKey(), e -> e.getValue().equals(second.get(e.getKey()))));
    }

}
