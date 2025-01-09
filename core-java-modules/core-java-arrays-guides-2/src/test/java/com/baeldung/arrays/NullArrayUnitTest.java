package com.baeldung.arrays;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import com.baeldung.array.NullArray;

public class NullArrayUnitTest {

    @Test
    public void whenArrayIsNull_thenReturnEmptyList() {
        String[] possiblyNullArray = null;

        Assertions.assertThat(NullArray.getAsListJava8(possiblyNullArray)).isNotNull().isEmpty();
        Assertions.assertThat(NullArray.getAsListTernary(possiblyNullArray)).isNotNull().isEmpty();
        Assertions.assertThat(NullArray.getAsListApacheCommons(possiblyNullArray)).isNotNull().isEmpty();
    }

    @Test
    public void whenArrayIsEmpty_thenReturnEmptyList() {
        String[] possiblyNullArray = {};

        Assertions.assertThat(NullArray.getAsListJava8(possiblyNullArray)).isNotNull().isEmpty();
        Assertions.assertThat(NullArray.getAsListTernary(possiblyNullArray)).isNotNull().isEmpty();
        Assertions.assertThat(NullArray.getAsListApacheCommons(possiblyNullArray)).isNotNull().isEmpty();
    }

    @Test
    public void whenArrayIsNotEmpty_thenReturnListWithSameElements() {
        String[] possiblyNullArray = {"a", "b"};

        Assertions.assertThat(NullArray.getAsListJava8(possiblyNullArray)).isNotNull().containsExactly(possiblyNullArray);
        Assertions.assertThat(NullArray.getAsListTernary(possiblyNullArray)).containsExactly(possiblyNullArray);
        Assertions.assertThat(NullArray.getAsListApacheCommons(possiblyNullArray)).containsExactly(possiblyNullArray);
    }

}
