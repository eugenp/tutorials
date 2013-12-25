package org.baeldung.java8;

import java.util.Collections;
import java.util.List;

import org.baeldung.java8.entity.Human;
import org.junit.Test;

import com.google.common.collect.Lists;

public class Java8SortUnitTest {

    // tests -

    @Test
    public final void whenSortingEntitiesByName_thenCorrectlySorted() {
        final List<Human> humans = Lists.newArrayList(new Human("Sarah", 10), new Human("Jack", 12));
        Collections.sort(humans, (final Human h1, final Human h2) -> h1.getName().compareTo(h2.getName()));
        // Assert.assertThat(actual, matcher);
    }

}
