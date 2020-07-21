package com.baeldung.arraycompare;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

public class EqualsCompareUnitTest {

    @Test
    public void givenSameContents_whenEquals_thenTrue() {
        final String[] planes1 = new String[] { "A320", "B738", "A321", "A319", "B77W", "B737", "A333", "A332" };
        final String[] planes2 = new String[] { "A320", "B738", "A321", "A319", "B77W", "B737", "A333", "A332" };

        assertThat(Arrays.equals(planes1, planes2)).isTrue();
    }

    @Test
    public void givenSameContentsDifferentOrder_whenEquals_thenFalse() {
        final String[] planes1 = new String[] { "A320", "B738", "A321", "A319", "B77W", "B737", "A333", "A332" };
        final String[] planes2 = new String[] { "B738", "A320", "A321", "A319", "B77W", "B737", "A333", "A332" };

        assertThat(Arrays.equals(planes1, planes2)).isFalse();
    }
}
