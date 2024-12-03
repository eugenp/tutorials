package com.baeldung.arraycompare;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

public class DeepEqualsCompareUnitTest {

    @Test
    public void givenSameContents_whenDeepEquals_thenTrue() {
        final Plane[][] planes1 = new Plane[][] { new Plane[] { new Plane("Plane 1", "A320") },
            new Plane[] { new Plane("Plane 2", "B738") } };
        final Plane[][] planes2 = new Plane[][] { new Plane[] { new Plane("Plane 1", "A320") },
            new Plane[] { new Plane("Plane 2", "B738") } };

        assertThat(Arrays.deepEquals(planes1, planes2)).isTrue();
    }

    @Test
    public void givenSameContentsWithDifferentOrder_whenDeepEquals_thenFalse() {
        final Plane[][] planes1 = new Plane[][] { new Plane[] { new Plane("Plane 1", "A320") },
            new Plane[] { new Plane("Plane 2", "B738") } };
        final Plane[][] planes2 = new Plane[][] { new Plane[] { new Plane("Plane 2", "B738") },
            new Plane[] { new Plane("Plane 1", "A320") } };

        assertThat(Arrays.deepEquals(planes1, planes2)).isFalse();
    }
}
