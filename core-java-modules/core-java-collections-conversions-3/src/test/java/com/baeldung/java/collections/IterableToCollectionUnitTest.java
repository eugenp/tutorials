package com.baeldung.java.collections;

import static org.hamcrest.Matchers.contains;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.apache.commons.collections4.IterableUtils;
import org.apache.commons.collections4.IteratorUtils;
import org.junit.Test;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;

public class IterableToCollectionUnitTest {

    Iterable<String> iterable = Arrays.asList("john", "tom", "jane");
    Iterator<String> iterator = iterable.iterator();

    @Test
    public void whenConvertIterableToListUsingJava_thenSuccess() {
        List<String> result = new ArrayList<String>();
        for (String str : iterable) {
            result.add(str);
        }

        assertThat(result, contains("john", "tom", "jane"));
    }

    @Test
    public void whenConvertIterableToListUsingJava8_thenSuccess() {
        List<String> result = new ArrayList<String>();
        iterable.forEach(result::add);

        assertThat(result, contains("john", "tom", "jane"));
    }

    @Test
    public void whenConvertIterableToListUsingJava8WithSpliterator_thenSuccess() {
        List<String> result = StreamSupport.stream(iterable.spliterator(), false)
            .collect(Collectors.toList());

        assertThat(result, contains("john", "tom", "jane"));
    }

    @Test
    public void whenConvertIterableToListUsingGuava_thenSuccess() {
        List<String> result = Lists.newArrayList(iterable);

        assertThat(result, contains("john", "tom", "jane"));
    }

    @Test
    public void whenConvertIterableToImmutableListUsingGuava_thenSuccess() {
        List<String> result = ImmutableList.copyOf(iterable);

        assertThat(result, contains("john", "tom", "jane"));
    }

    @Test
    public void whenConvertIterableToListUsingApacheCommons_thenSuccess() {
        List<String> result = IterableUtils.toList(iterable);

        assertThat(result, contains("john", "tom", "jane"));
    }

    // ======================== Iterator

    @Test
    public void whenConvertIteratorToListUsingJava_thenSuccess() {
        List<String> result = new ArrayList<String>();
        while (iterator.hasNext()) {
            result.add(iterator.next());
        }

        assertThat(result, contains("john", "tom", "jane"));
    }

    @Test
    public void whenConvertIteratorToListUsingJava8_thenSuccess() {
        List<String> result = new ArrayList<String>();
        iterator.forEachRemaining(result::add);

        assertThat(result, contains("john", "tom", "jane"));
    }

    @Test
    public void whenConvertIteratorToListUsingJava8WithSpliterator_thenSuccess() {
        List<String> result = StreamSupport.stream(Spliterators.spliteratorUnknownSize(iterator, Spliterator.ORDERED), false)
            .collect(Collectors.toList());

        assertThat(result, contains("john", "tom", "jane"));
    }

    @Test
    public void whenConvertIteratorToListUsingGuava_thenSuccess() {
        List<String> result = Lists.newArrayList(iterator);

        assertThat(result, contains("john", "tom", "jane"));
    }

    @Test
    public void whenConvertIteratorToImmutableListUsingGuava_thenSuccess() {
        List<String> result = ImmutableList.copyOf(iterator);

        assertThat(result, contains("john", "tom", "jane"));
    }

    @Test
    public void whenConvertIteratorToListUsingApacheCommons_thenSuccess() {
        List<String> result = IteratorUtils.toList(iterator);

        assertThat(result, contains("john", "tom", "jane"));
    }
}
