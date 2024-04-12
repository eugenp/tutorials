package com.baeldung.object.type;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class TreeSorterAssertJUnitTest {
    private final TreeSorter tested = new TreeSorter();

    @Test
    public void sortTreeShouldReturnEvergreen_WhenPineIsPassed() {
        Tree tree = tested.sortTree("Pine");
        assertThat(tree).isExactlyInstanceOf(Evergreen.class);
    }

    @Test
    public void sortTreeShouldReturnDecidious_WhenBirchIsPassed() {
        Tree tree = tested.sortTree("Birch");
        assertThat(tree).hasSameClassAs(new Deciduous("Birch"));
    }

}