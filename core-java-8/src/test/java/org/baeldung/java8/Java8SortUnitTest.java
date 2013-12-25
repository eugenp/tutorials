package org.baeldung.java8;

import static org.hamcrest.Matchers.equalTo;

import java.util.Collections;
import java.util.List;

import org.baeldung.java8.entity.Human;
import org.junit.Assert;
import org.junit.Test;

import com.google.common.collect.Lists;
import com.google.common.primitives.Ints;

public class Java8SortUnitTest {

    // tests -

    @Test
    public final void whenSortingEntitiesByName_thenCorrectlySorted() {
        final List<Human> humans = Lists.newArrayList(new Human("Sarah", 10), new Human("Jack", 12));
        Collections.sort(humans, (final Human h1, final Human h2) -> h1.getName().compareTo(h2.getName()));
        Assert.assertThat(humans.get(0), equalTo(new Human("Jack", 12)));
    }

    @Test
    public final void whenSortingEntitiesByAge_thenCorrectlySorted() {
        final List<Human> humans = Lists.newArrayList(new Human("Sarah", 10), new Human("Jack", 12));
        Collections.sort(humans, (final Human h1, final Human h2) -> Ints.compare(h1.getAge(), h2.getAge()));
        Assert.assertThat(humans.get(0), equalTo(new Human("Sarah", 10)));
    }

}
