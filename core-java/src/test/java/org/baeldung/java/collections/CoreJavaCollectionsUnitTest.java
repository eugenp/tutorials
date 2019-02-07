package org.baeldung.java.collections;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.apache.commons.collections4.ListUtils;
import org.junit.Test;

import com.google.common.collect.ImmutableList;

public class CoreJavaCollectionsUnitTest {

    // tests -

    @Test
    public final void givenUsingTheJdk_whenArrayListIsSynchronized_thenCorrect() {
        final List<String> list = new ArrayList<String>(Arrays.asList("one", "two", "three"));
        final List<String> synchronizedList = Collections.synchronizedList(list);
        System.out.println("Synchronized List is: " + synchronizedList);
    }

    @Test(expected = UnsupportedOperationException.class)
    public final void givenUsingTheJdk_whenUnmodifiableListIsCreatedFromOriginal_thenNoLongerModifiable() {
        final List<String> list = new ArrayList<String>(Arrays.asList("one", "two", "three"));
        final List<String> unmodifiableList = Collections.unmodifiableList(list);
        unmodifiableList.add("four");
    }

    @Test(expected = UnsupportedOperationException.class)
    public final void givenUsingGuava_whenUnmodifiableListIsCreatedFromOriginal_thenNoLongerModifiable() {
        final List<String> list = new ArrayList<String>(Arrays.asList("one", "two", "three"));
        final List<String> unmodifiableList = ImmutableList.copyOf(list);
        unmodifiableList.add("four");
    }

    @Test(expected = UnsupportedOperationException.class)
    public final void givenUsingGuavaBuilder_whenUnmodifiableListIsCreatedFromOriginal_thenNoLongerModifiable() {
        final List<String> list = new ArrayList<String>(Arrays.asList("one", "two", "three"));
        final ImmutableList<Object> unmodifiableList = ImmutableList.builder().addAll(list).build();
        unmodifiableList.add("four");
    }

    @Test(expected = UnsupportedOperationException.class)
    public final void givenUsingCommonsCollections_whenUnmodifiableListIsCreatedFromOriginal_thenNoLongerModifiable() {
        final List<String> list = new ArrayList<String>(Arrays.asList("one", "two", "three"));
        final List<String> unmodifiableList = ListUtils.unmodifiableList(list);
        unmodifiableList.add("four");
    }

}
