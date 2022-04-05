package com.baeldung.java9.list.immutable;

import com.google.common.collect.ImmutableList;
import org.apache.commons.collections4.ListUtils;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ImmutableArrayListUnitTest {

    @Test(expected = UnsupportedOperationException.class)
    public final void givenUsingTheJdk_whenUnmodifiableListIsCreated_thenNotModifiable() {
        final List<String> list = new ArrayList<>(Arrays.asList("one", "two", "three"));
        final List<String> unmodifiableList = Collections.unmodifiableList(list);
        unmodifiableList.add("four");
    }

    @Test(expected = UnsupportedOperationException.class)
    public final void givenUsingTheJava9_whenUnmodifiableListIsCreated_thenNotModifiable() {
        final List<String> list = new ArrayList<>(Arrays.asList("one", "two", "three"));
        final List<String> unmodifiableList = List.of(list.toArray(new String[]{}));
        unmodifiableList.add("four");
    }

    @Test(expected = UnsupportedOperationException.class)
    public final void givenUsingGuava_whenUnmodifiableListIsCreated_thenNotModifiable() {
        final List<String> list = new ArrayList<>(Arrays.asList("one", "two", "three"));
        final List<String> unmodifiableList = ImmutableList.copyOf(list);
        unmodifiableList.add("four");
    }

    @Test(expected = UnsupportedOperationException.class)
    public final void givenUsingGuavaBuilder_whenUnmodifiableListIsCreated_thenNoLongerModifiable() {
        final List<String> list = new ArrayList<>(Arrays.asList("one", "two", "three"));
        final ImmutableList<String> unmodifiableList = ImmutableList.<String>builder().addAll(list).build();
        unmodifiableList.add("four");
    }

    @Test(expected = UnsupportedOperationException.class)
    public final void givenUsingCommonsCollections_whenUnmodifiableListIsCreated_thenNotModifiable() {
        final List<String> list = new ArrayList<>(Arrays.asList("one", "two", "three"));
        final List<String> unmodifiableList = ListUtils.unmodifiableList(list);
        unmodifiableList.add("four");
    }
}
