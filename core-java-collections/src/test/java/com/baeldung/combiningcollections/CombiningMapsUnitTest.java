package com.baeldung.combiningcollections;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

public class CombiningMapsUnitTest {
    private static final Map<String, String> first = new HashMap<>();
    private static final Map<String, String> second = new HashMap<>();
    private static Map<String, String> expected = new HashMap<>();
    
    static {
    	first.put("one", "first String");
    	first.put("two", "second String");
    	
    	second.put("three", "third String");
    	second.put("four", "fourth String");

    	expected.put("one", "first String");
    	expected.put("two", "second String");
    	expected.put("three", "third String");
    	expected.put("four", "fourth String");
    }
    
    @Test
    public void givenTwoMaps_whenUsingNativeJava_thenMapsCombined() {
    	assertThat(CombiningMaps.usingPlainJava(first, second), is(expected));
    }

    
    @Test
    public void givenTwoMaps_whenUsingForEach_thenMapsCombined() {
    	assertThat(CombiningMaps.usingJava8ForEach(first, second), is(expected));
    }
    
    @Test
    public void givenTwoMaps_whenUsingFlatMaps_thenMapsCombined() {
    	assertThat(CombiningMaps.usingJava8FlatMaps(first, second), is(expected));
    }
    
    @Test
    public void givenTwoMaps_whenUsingApacheCommons_thenMapsCombined() {
    	assertThat(CombiningMaps.usingApacheCommons(first, second), is(expected));
    }
    
    @Test
    public void givenTwoMaps_whenUsingGuava_thenMapsCombined() {
    	assertThat(CombiningMaps.usingGuava(first, second), is(expected));
    }
}
