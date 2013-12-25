package org.baeldung.java8;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;

import java.util.Collections;
import java.util.List;

import org.baeldung.java8.entity.Human;
import org.junit.Test;

import com.google.common.collect.Lists;

public class Java8ComparatorUnitTest {

    // tests -

    @Test
    public final void when_thenCorrect() {
        final List<Human> humans = Lists.newArrayList(new Human(randomAlphabetic(5)), new Human(randomAlphabetic(5)));
        Collections.sort(humans, (final Human h1, final Human h2) -> h1.getName().compareTo(h2.getName()));
        System.out.println(humans);
    }

}
