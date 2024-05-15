package com.baeldung.object.type;

import org.junit.jupiter.api.Test;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

class TreeSorterHamcrestUnitTest {
    private final TreeSorter tested = new TreeSorter();

    @Test
    public void sortTreeShouldReturnEvergreen_WhenPineIsPassed() {
        Tree tree = tested.sortTree("Pine");
        //with JUnit assertEquals:
        assertEquals(tree.getClass(), Evergreen.class);
        //with Hamcrest instanceOf:
        assertThat(tree, instanceOf(Evergreen.class));

    }

    @Test
    public void sortTreeShouldReturnDecidious_WhenBirchIsPassed() {
        Tree tree = tested.sortTree("Birch");
        assertThat(tree, instanceOf(Deciduous.class));
    }

}