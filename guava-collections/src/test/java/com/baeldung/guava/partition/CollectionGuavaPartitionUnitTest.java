package com.baeldung.guava.partition;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

import java.util.Collection;
import java.util.List;

import org.junit.Test;

import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;

public class CollectionGuavaPartitionUnitTest {

    // tests - guava

    @Test
    public final void givenList_whenParitioningIntoNSublists_thenCorrect() {
        final List<Integer> intList = Lists.newArrayList(1, 2, 3, 4, 5, 6, 7, 8);

        final List<List<Integer>> subSets = Lists.partition(intList, 3);

        // When
        final List<Integer> lastPartition = subSets.get(2);
        final List<Integer> expectedLastPartition = Lists.<Integer> newArrayList(7, 8);
        assertThat(subSets.size(), equalTo(3));
        assertThat(lastPartition, equalTo(expectedLastPartition));
    }

    @Test
    public final void givenListPartitioned_whenOriginalListIsModified_thenPartitionsChangeAsWell() {
        // Given
        final List<Integer> intList = Lists.newArrayList(1, 2, 3, 4, 5, 6, 7, 8);
        final List<List<Integer>> subSets = Lists.partition(intList, 3);

        // When
        intList.add(9);
        final List<Integer> lastPartition = subSets.get(2);
        final List<Integer> expectedLastPartition = Lists.<Integer> newArrayList(7, 8, 9);
        assertThat(lastPartition, equalTo(expectedLastPartition));
    }

    @Test
    public final void givenCollection_whenParitioningIntoNSublists_thenCorrect() {
        final Collection<Integer> intCollection = Lists.newArrayList(1, 2, 3, 4, 5, 6, 7, 8);

        final Iterable<List<Integer>> subSets = Iterables.partition(intCollection, 3);

        // When
        final List<Integer> firstPartition = subSets.iterator().next();
        final List<Integer> expectedLastPartition = Lists.<Integer> newArrayList(1, 2, 3);
        assertThat(firstPartition, equalTo(expectedLastPartition));
    }

}
