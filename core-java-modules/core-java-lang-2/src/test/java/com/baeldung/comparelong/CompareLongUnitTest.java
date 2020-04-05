package com.baeldung.comparelong;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

import org.junit.Test;

import java.util.Objects;

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
    public void givenLongValuesLessThan128_whenUsingObjectsEquals_thenSuccess() {

        Long l1 = 127L;
        Long l2 = 127L;

        assertThat(Objects.equals(l1, l2)).isTrue();
    }

    @Test
    public void givenLongValuesGreaterOrEqualsThan128_whenUsingObjectsEquals_thenSuccess() {

        Long l1 = 128L;
        Long l2 = 128L;

        assertThat(Objects.equals(l1, l2)).isTrue();
    }

    @Test
    public void givenNullReference_whenUsingObjectsEquals_thenNoException() {

        Long l1 = null;
        Long l2 = 128L;

        assertThatCode(() -> Objects.equals(l1, l2)).doesNotThrowAnyException();
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