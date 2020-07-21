package com.baeldung.arraycompare;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class LengthsCompareUnitTest {

    @Test
    public void givenSameContent_whenSizeCompare_thenTrue() {
        final String[] planes1 = new String[] { "A320", "B738", "A321", "A319", "B77W", "B737", "A333", "A332" };
        final Integer[] quantities = new Integer[] { 10, 12, 34, 45, 12, 43, 5, 2 };

        assertThat(planes1).hasSize(8);
        assertThat(quantities).hasSize(8);
    }
}

