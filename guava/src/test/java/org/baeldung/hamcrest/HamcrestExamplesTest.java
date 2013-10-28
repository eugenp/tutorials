package org.baeldung.hamcrest;

import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.emptyArray;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertThat;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class HamcrestExamplesTest {

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
        assertThat(collection, hasItems("cd", "ef"));
    }

    @Test
    public final void whenVerifyingMultipleElementsArePartOfCollection_thenCorrect2() {
        final List<String> collection = Lists.newArrayList("ab", "cd", "ef");
        assertThat(collection, contains("ab", "cd", "ef"));
    }

    @Test
    public final void givenCollectionIsEmpty_whenChecking_thenEmpty() {
        final List<String> collection = Lists.newArrayList();
        assertThat(collection, empty());
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

}
