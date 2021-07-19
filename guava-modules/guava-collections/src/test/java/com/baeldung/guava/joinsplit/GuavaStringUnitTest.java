package com.baeldung.guava.joinsplit;

import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.containsString;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import com.google.common.base.Function;
import com.google.common.base.Joiner;
import com.google.common.base.Splitter;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class GuavaStringUnitTest {

    @Test
    public void whenConvertListToString_thenConverted() {
        final List<String> names = Lists.newArrayList("John", "Jane", "Adam", "Tom");
        final String result = Joiner.on(",").join(names);

        assertEquals(result, "John,Jane,Adam,Tom");
    }

    @Test
    public void whenConvertListToStringAndSkipNull_thenConverted() {
        final List<String> names = Lists.newArrayList("John", null, "Jane", "Adam", "Tom");
        final String result = Joiner.on(",").skipNulls().join(names);

        assertEquals(result, "John,Jane,Adam,Tom");
    }

    @Test
    public void whenConvertMapToString_thenConverted() {
        final Map<String, Integer> salary = Maps.newHashMap();
        salary.put("John", 1000);
        salary.put("Jane", 1500);

        final String result = Joiner.on(" , ").withKeyValueSeparator(" = ").join(salary);

        assertThat(result, containsString("John = 1000"));
        assertThat(result, containsString("Jane = 1500"));
    }

    @Test
    public void whenJoinNestedCollections_thenJoined() {
        final List<ArrayList<String>> nested = Lists.newArrayList(Lists.newArrayList("apple", "banana", "orange"), Lists.newArrayList("cat", "dog", "bird"), Lists.newArrayList("John", "Jane", "Adam"));
        final String result = Joiner.on(";").join(Iterables.transform(nested, new Function<List<String>, String>() {
            @Override
            public final String apply(final List<String> input) {
                return Joiner.on("-").join(input);
            }
        }));

        assertThat(result, containsString("apple-banana-orange"));
        assertThat(result, containsString("cat-dog-bird"));
        assertThat(result, containsString("John-Jane-Adam"));
    }

    @Test
    public void whenUseForNull_thenUsed() {
        final List<String> names = Lists.newArrayList("John", null, "Jane", "Adam", "Tom");
        final String result = Joiner.on(",").useForNull("nameless").join(names);

        assertEquals(result, "John,nameless,Jane,Adam,Tom");
    }

    @Test
    public void whenCreateListFromString_thenCreated() {
        final String input = "apple - banana - orange";
        final List<String> result = Splitter.on("-").trimResults().splitToList(input);

        assertThat(result, contains("apple", "banana", "orange"));
    }

    @Test
    public void whenCreateMapFromString_thenCreated() {
        final String input = "John=first,Adam=second";
        final Map<String, String> result = Splitter.on(",").withKeyValueSeparator("=").split(input);

        assertEquals("first", result.get("John"));
        assertEquals("second", result.get("Adam"));
    }

    @Test
    public void whenSplitStringOnMultipleSeparator_thenSplit() {
        final String input = "apple.banana,,orange,,.";
        final List<String> result = Splitter.onPattern("[.,]").omitEmptyStrings().splitToList(input);

        assertThat(result, contains("apple", "banana", "orange"));
    }

    @Test
    public void whenSplitStringOnSpecificLength_thenSplit() {
        final String input = "Hello world";
        final List<String> result = Splitter.fixedLength(3).splitToList(input);

        assertThat(result, contains("Hel", "lo ", "wor", "ld"));
    }

    @Test
    public void whenLimitSplitting_thenLimited() {
        final String input = "a,b,c,d,e";
        final List<String> result = Splitter.on(",").limit(4).splitToList(input);

        assertEquals(4, result.size());
        assertThat(result, contains("a", "b", "c", "d,e"));
    }
}
