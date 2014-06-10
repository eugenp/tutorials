package org.baeldung.java.collections;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertThat;

import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.PredicateUtils;
import org.junit.Test;

import com.google.common.base.Predicates;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;

public class JavaCollectionCleanupUnitTest {

    // removing nulls

    @Test
    public final void givenListContainsNulls_whenRemovingNullsWithPlainJava_thenCorrect() {
        final List<Integer> list = Lists.newArrayList(null, 1, null);
        while (list.remove(null))
            ;

        assertThat(list, hasSize(1));
    }

    @Test
    public final void givenListContainsNulls_whenRemovingNullsWithGuavaV1_thenCorrect() {
        final List<Integer> listWithNulls = Lists.newArrayList(null, 1, null);
        Iterables.removeIf(listWithNulls, Predicates.isNull());

        assertThat(listWithNulls, hasSize(1));
    }

    @Test
    public final void givenListContainsNulls_whenRemovingNullsWithGuavaV2_thenCorrect() {
        final List<Integer> listWithNulls = Lists.newArrayList(null, 1, null, 2, 3);
        final List<Integer> listWithoutNulls = Lists.newArrayList(Iterables.filter(listWithNulls, Predicates.notNull()));

        assertThat(listWithoutNulls, hasSize(3));
    }

    @Test
    public final void givenListContainsNulls_whenRemovingNullsWithCommonsCollections_thenCorrect() {
        final List<Integer> list = Lists.newArrayList(null, 1, 2, null, 3, null);
        CollectionUtils.filter(list, PredicateUtils.notNullPredicate());

        assertThat(list, hasSize(3));
    }

}
