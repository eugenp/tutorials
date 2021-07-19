package com.baeldung.hamcrest;

import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.emptyArray;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.everyItem;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertThat;
import static org.hamcrest.Matchers.emptyIterable;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.hamcrest.Matchers;
import org.junit.Test;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class HamcrestExamplesUnitTest {

    // tests

    @Test
    public final void whenVerifyingSingleElementIsPartOfCollection_thenCorrect() {
        final List<String> collection = Lists.newArrayList("ab", "cd", "ef");
        assertThat(collection, hasItem("cd"));
        assertThat(collection, not(hasItem("zz")));
    }

    @Test
    public final void whenVerifyingMultipleElementsArePartOfCollection_thenCorrect1() {
        final List<String> collection = Lists.newArrayList("ab", "cd", "ef");
        assertThat(collection, hasItems("ef", "cd"));
    }

    @Test
    public final void whenVerifyingMultipleElementsArePartOfCollectionInStrictOrder_thenCorrect2() {
        final List<String> collection = Lists.newArrayList("ab", "cd", "ef");
        assertThat(collection, contains("ab", "cd", "ef"));
    }

    @Test
    public final void whenVerifyingMultipleElementsArePartOfCollectionInAnyOrder_thenCorrect2() {
        final List<String> collection = Lists.newArrayList("ab", "cd", "ef");
        assertThat(collection, containsInAnyOrder("cd", "ab", "ef"));
    }

    @Test
    public final void givenCollectionIsEmpty_whenChecking_thenEmpty() {
        final List<String> collection = Lists.newArrayList();
        assertThat(collection, empty());
    }
    
    @Test
    public final void givenIterableIsEmpty_whenChecking_thenEmpty() {
        final Iterable<String> collection = Lists.newArrayList();
        assertThat(collection, emptyIterable());
    }

    @Test
    public final void givenCollectionIsNotEmpty_whenChecking_thenNotEmpty() {
        final List<String> collection = Lists.newArrayList("a");
        assertThat(collection, not(empty()));
    }

    @Test
    public final void givenMapIsEmpty_whenChecking_thenEmpty() {
        final Map<String, String> collection = Maps.newHashMap();
        assertThat(collection, equalTo(Collections.EMPTY_MAP));
    }

    @Test
    public final void givenArrayIsEmpty_whenChecking_thenEmpty() {
        final String[] array = new String[] { "ab" };
        assertThat(array, not(emptyArray()));
    }

    @Test
    public final void whenCollectionSizeIsChecked_thenCorrect() {
        final List<String> collection = Lists.newArrayList("ab", "cd", "ef");
        assertThat(collection, hasSize(3));
    }

    @Test
    public final void whenIterableSizeIsChecked_thenCorrect() {
        final Iterable<String> collection = Lists.newArrayList("ab", "cd", "ef");
        assertThat(collection, Matchers.<String> iterableWithSize(3));
    }

    @Test
    public final void whenCheckingConditionOverEachItem_thenCorrect() {
        final List<Integer> collection = Lists.newArrayList(15, 20, 25, 30);
        assertThat(collection, everyItem(greaterThan(10)));
    }

}
