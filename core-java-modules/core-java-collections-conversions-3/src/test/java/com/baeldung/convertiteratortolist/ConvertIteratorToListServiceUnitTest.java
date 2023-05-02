package com.baeldung.convertiteratortolist;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.collection.IsIterableContainingInAnyOrder.containsInAnyOrder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.apache.commons.collections4.IteratorUtils;
import org.junit.Before;
import org.junit.Test;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;

public class ConvertIteratorToListServiceUnitTest {

    Iterator<Integer> iterator;

    @Before
    public void setUp() {
        iterator = Arrays.asList(1, 2, 3)
            .iterator();
    }

    @Test
    public void givenAnIterator_whenConvertIteratorToListUsingWhileLoop_thenReturnAList() {

        List<Integer> actualList = new ArrayList<>();

        // Convert Iterator to List using while loop dsf
        while (iterator.hasNext()) {
            actualList.add(iterator.next());
        }

        assertThat(actualList, hasSize(3));
        assertThat(actualList, containsInAnyOrder(1, 2, 3));
    }

    @Test
    public void givenAnIterator_whenConvertIteratorToListAfterJava8_thenReturnAList() {
        List<Integer> actualList = new ArrayList<>();

        // Convert Iterator to List using Java 8
        iterator.forEachRemaining(actualList::add);

        assertThat(actualList, hasSize(3));
        assertThat(actualList, containsInAnyOrder(1, 2, 3));
    }

    @Test
    public void givenAnIterator_whenConvertIteratorToListJava8Stream_thenReturnAList() {

        // Convert iterator to iterable
        Iterable<Integer> iterable = () -> iterator;

        // Extract List from stream
        List<Integer> actualList = StreamSupport
            .stream(iterable.spliterator(), false)
            .collect(Collectors.toList());

        assertThat(actualList, hasSize(3));
        assertThat(actualList, containsInAnyOrder(1, 2, 3));
    }

    @Test
    public void givenAnIterator_whenConvertIteratorToImmutableListWithGuava_thenReturnAList() {

        // Convert Iterator to an Immutable list using Guava library in Java
        List<Integer> actualList = ImmutableList.copyOf(iterator);

        assertThat(actualList, hasSize(3));
        assertThat(actualList, containsInAnyOrder(1, 2, 3));
    }

    @Test
    public void givenAnIterator_whenConvertIteratorToMutableListWithGuava_thenReturnAList() {

        // Convert Iterator to a mutable list using Guava library in Java
        List<Integer> actualList = Lists.newArrayList(iterator);

        assertThat(actualList, hasSize(3));
        assertThat(actualList, containsInAnyOrder(1, 2, 3));
    }

    @Test
    public void givenAnIterator_whenConvertIteratorToMutableListWithApacheCommons_thenReturnAList() {

        // Convert Iterator to a mutable list using Apache Commons library in Java
        List<Integer> actualList = IteratorUtils.toList(iterator);

        assertThat(actualList, hasSize(3));
        assertThat(actualList, containsInAnyOrder(1, 2, 3));
    }
}
