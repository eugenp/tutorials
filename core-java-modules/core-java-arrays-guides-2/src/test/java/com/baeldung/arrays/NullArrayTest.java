package com.baeldung.arrays;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import com.baeldung.array.NullArray.ApacheCommons;
import com.baeldung.array.NullArray.Java8;
import com.baeldung.array.NullArray.TernaryOperator;

public class NullArrayTest {

    @Test
    public void whenArrayIsNull_thenReturnEmptyList() {
        String[] possiblyNullArray = null;

        Assertions.assertThat(Java8.getAsList(possiblyNullArray)).isNotNull().isEmpty();
        Assertions.assertThat(TernaryOperator.getAsList(possiblyNullArray)).isNotNull().isEmpty();
        Assertions.assertThat(ApacheCommons.getAsList(possiblyNullArray)).isNotNull().isEmpty();
    }

    @Test
    public void whenArrayIsEmpty_thenReturnEmptyList() {
        String[] possiblyNullArray = {};

        Assertions.assertThat(Java8.getAsList(possiblyNullArray)).isNotNull().isEmpty();
        Assertions.assertThat(TernaryOperator.getAsList(possiblyNullArray)).isNotNull().isEmpty();
        Assertions.assertThat(ApacheCommons.getAsList(possiblyNullArray)).isNotNull().isEmpty();
    }

    @Test
    public void whenArrayIsNotEmpty_thenReturnListWithSameElements() {
        String[] possiblyNullArray = {"a", "b"};

        Assertions.assertThat(Java8.getAsList(possiblyNullArray)).isNotNull().containsExactly(possiblyNullArray);
        Assertions.assertThat(TernaryOperator.getAsList(possiblyNullArray)).containsExactly(possiblyNullArray);
        Assertions.assertThat(ApacheCommons.getAsList(possiblyNullArray)).containsExactly(possiblyNullArray);
    }

}
