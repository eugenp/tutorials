package com.baeldung;

import org.junit.jupiter.api.Test;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.collection.IsArrayWithSize.arrayWithSize;
import static org.junit.Assert.assertThat;

public class LengthsCompareUnitTest {

    @Test
    public void givenArray1andArray2_whenSameSizes_thenSizeEqualsOk() {
        final String[] planes1 = new String[] { "A320", "B738", "A321", "A319", "B77W", "B737", "A333", "A332" };
        final Integer[] quantities = new Integer[] { 10, 12, 34, 45, 12, 43, 5, 2 };

        assertThat(planes1, arrayWithSize(8));
        assertThat(quantities, arrayWithSize(8));
        assertThat(planes1.length, is(8));
        assertThat(quantities.length, is(8));
    }
}

