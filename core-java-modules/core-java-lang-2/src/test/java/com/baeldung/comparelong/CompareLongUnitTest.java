package com.baeldung.comparelong;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.Test;

public class CompareLongUnitTest {

    @Test
    public void givenLongValuesLessThan128_whenUsingReferenceComparater_thenSuccess() {

        Long l1 = 127L;
        Long l2 = 127L;

        assertThat(l1 == l2).isTrue();
    }

    @Test
    public void givenLongValuesGreaterOrEqualsThan128_whenUsingReferenceComparater_thenFails() {

        Long l1 = 128L;
        Long l2 = 128L;

        assertThat(l1 == l2).isFalse();
    }

    @Test
    public void givenLongValuesGreaterOrEqualsThan128_whenUsingEquals_thenSuccess() {

        Long l1 = 128L;
        Long l2 = 128L;

        assertThat(l1.equals(l2)).isTrue();
    }

    @Test
    public void givenLongValuesGreaterOrEqualsThan128_whenUsingComparisonOperator_andLongValue_thenSuccess() {

        Long l1 = 128L;
        Long l2 = 128L;

        assertThat(l1.longValue() == l2.longValue()).isTrue();
    }

    @Test
    public void givenLongValuesGreaterOrEqualsThan128_whenUsingCasting_thenSuccess() {

        Long l1 = 128L;
        Long l2 = 128L;

        assertThat((long) l1 == (long) l2).isTrue();
    }

}