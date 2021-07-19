package com.baeldung.collections;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.containsInRelativeOrder;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.PredicateUtils;
import org.junit.Test;

import com.google.common.base.Predicates;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

public class JavaCollectionCleanupUnitTest {

    // tests - removing nulls

    @Test
    public void givenListContainsNulls_whenRemovingNullsWithPlainJava_thenCorrect() {
        final List<Integer> list = Lists.newArrayList(null, 1, null);
        while (list.remove(null))
            ;

        assertThat(list, hasSize(1));
    }

    @Test
    public void givenListContainsNulls_whenRemovingNullsWithPlainJavaAlternative_thenCorrect() {
        final List<Integer> list = Lists.newArrayList(null, 1, null);
        list.removeAll(Collections.singleton(null));

        assertThat(list, hasSize(1));
    }

    @Test
    public void givenListContainsNulls_whenRemovingNullsWithGuavaV1_thenCorrect() {
        final List<Integer> list = Lists.newArrayList(null, 1, null);
        Iterables.removeIf(list, Predicates.isNull());

        assertThat(list, hasSize(1));
    }

    @Test
    public void givenListContainsNulls_whenRemovingNullsWithGuavaV2_thenCorrect() {
        final List<Integer> list = Lists.newArrayList(null, 1, null, 2, 3);
        final List<Integer> listWithoutNulls = Lists.newArrayList(Iterables.filter(list, Predicates.notNull()));

        assertThat(listWithoutNulls, hasSize(3));
    }

    @Test
    public void givenListContainsNulls_whenRemovingNullsWithCommonsCollections_thenCorrect() {
        final List<Integer> list = Lists.newArrayList(null, 1, 2, null, 3, null);
        CollectionUtils.filter(list, PredicateUtils.notNullPredicate());

        assertThat(list, hasSize(3));
    }

    // tests - remove duplicates

    @Test
    public void givenListContainsDuplicates_whenRemovingDuplicatesWithPlainJava_thenCorrect() {
        final List<Integer> listWithDuplicates = Lists.newArrayList(5, 0, 3, 1, 2, 3, 0, 0);
        final List<Integer> listWithoutDuplicates = new ArrayList<>(new HashSet<>(listWithDuplicates));

        assertThat(listWithoutDuplicates, hasSize(5));
        assertThat(listWithoutDuplicates, containsInAnyOrder(5, 0, 3, 1, 2));
    }

    @Test
    public void givenListContainsDuplicates_whenRemovingDuplicatesPreservingOrderWithPlainJava_thenCorrect() {
        final List<Integer> listWithDuplicates = Lists.newArrayList(5, 0, 3, 1, 2, 3, 0, 0);
        final List<Integer> listWithoutDuplicates = new ArrayList<>(new LinkedHashSet<>(listWithDuplicates));

        assertThat(listWithoutDuplicates, hasSize(5));
        assertThat(listWithoutDuplicates, containsInRelativeOrder(5, 0, 3, 1, 2));
    }

    @Test
    public void givenListContainsDuplicates_whenRemovingDuplicatesWithGuava_thenCorrect() {
        final List<Integer> listWithDuplicates = Lists.newArrayList(5, 0, 3, 1, 2, 3, 0, 0);
        final List<Integer> listWithoutDuplicates = Lists.newArrayList(Sets.newHashSet(listWithDuplicates));

        assertThat(listWithoutDuplicates, hasSize(5));
        assertThat(listWithoutDuplicates, containsInAnyOrder(5, 0, 3, 1, 2));
    }

    @Test
    public void givenListContainsDuplicates_whenRemovingDuplicatesPreservingOrderWithGuava_thenCorrect() {
        final List<Integer> listWithDuplicates = Lists.newArrayList(5, 0, 3, 1, 2, 3, 0, 0);
        final List<Integer> listWithoutDuplicates = Lists.newArrayList(Sets.newLinkedHashSet(listWithDuplicates));

        assertThat(listWithoutDuplicates, hasSize(5));
        assertThat(listWithoutDuplicates, containsInRelativeOrder(5, 0, 3, 1, 2));
    }
}
