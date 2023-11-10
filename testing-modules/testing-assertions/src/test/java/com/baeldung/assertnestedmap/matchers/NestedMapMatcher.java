package com.baeldung.assertnestedmap.matchers;


import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

import java.util.Map;

public class NestedMapMatcher<K, V> extends TypeSafeMatcher<Map<K, Object>> {
    private K key;
    private V subMapValue;

    public NestedMapMatcher(K key, V subMapValue) {
        this.key = key;
        this.subMapValue = subMapValue;
    }

    @Override
    protected boolean matchesSafely(Map<K, Object> item) {
        if (item.containsKey(key)) {
            Object actualValue = item.get(key);
            return subMapValue.equals(actualValue);
        }
        return false;
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("a map containing key ").appendValue(key)
                .appendText(" with value ").appendValue(subMapValue);
    }

    public static <K, V> Matcher<V> hasNestedMapEntry(K key, V expectedValue) {
        return new NestedMapMatcher(key, expectedValue);
    }
}

