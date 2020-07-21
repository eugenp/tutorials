package com.baeldung.arraycompare;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ReferenceCompareUnitTest {

    @Test
    public void givenSameReferences_whenSame_thenTrue() {
        final String[] planes1 = new String[] { "A320", "B738", "A321", "A319", "B77W", "B737", "A333", "A332" };
        final String[] planes2 = planes1;

        assertThat(planes1).isSameAs(planes2);

        planes2[0] = "747";

        assertThat(planes1).isSameAs(planes2);
        assertThat(planes2[0]).isEqualTo("747");
        assertThat(planes1[0]).isEqualTo("747");
    }

    @Test
    public void givenSameContentDifferentReferences_whenSame_thenFalse() {
        final String[] planes1 = new String[] { "A320", "B738", "A321", "A319", "B77W", "B737", "A333", "A332" };
        final String[] planes2 = new String[] { "A320", "B738", "A321", "A319", "B77W", "B737", "A333", "A332" };

        assertThat(planes1).isNotSameAs(planes2);
    }
}
