package org.baeldung.java8;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertThat;

import java.util.List;
import java.util.stream.Collectors;

import org.junit.Test;

import com.google.common.collect.Lists;

public class Java8CollectionCleanupUnitTest {

    // tests -

    @Test
    public void givenListContainsNulls_whenFilteringParallel_thenCorrect() {
        final List<Integer> list = Lists.newArrayList(null, 1, 2, null, 3, null);
        final List<Integer> listWithoutNulls = list.parallelStream().filter(i -> i != null).collect(Collectors.toList());

        assertThat(listWithoutNulls, hasSize(3));
    }

    @Test
    public void givenListContainsNulls_whenFilteringSerial_thenCorrect() {
        final List<Integer> list = Lists.newArrayList(null, 1, 2, null, 3, null);
        final List<Integer> listWithoutNulls = list.stream().filter(i -> i != null).collect(Collectors.toList());

        assertThat(listWithoutNulls, hasSize(3));
    }

    @Test
    public void givenListContainsDuplicates_whenRemovingDuplicatesWithJava8_thenCorrect() {
        final List<Integer> listWithDuplicates = Lists.newArrayList(1, 1, 2, 2, 3, 3);
        final List<Integer> listWithoutDuplicates = listWithDuplicates.parallelStream().distinct().collect(Collectors.toList());

        assertThat(listWithoutDuplicates, hasSize(3));
    }

}
