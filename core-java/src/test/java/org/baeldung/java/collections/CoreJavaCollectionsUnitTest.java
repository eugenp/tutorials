package org.baeldung.java.collections;

import com.google.common.collect.ImmutableList;
import org.apache.commons.collections4.ListUtils;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class CoreJavaCollectionsUnitTest {

    private static final Logger LOG = LoggerFactory.getLogger(CoreJavaCollectionsUnitTest.class);


    // tests -

    @Test
    public final void givenUsingTheJdk_whenArrayListIsSynchronized_thenCorrect() {
        final List<String> list = new ArrayList<String>(Arrays.asList("one", "two", "three"));
        final List<String> synchronizedList = Collections.synchronizedList(list);
        LOG.debug("Synchronized List is: " + synchronizedList);
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
