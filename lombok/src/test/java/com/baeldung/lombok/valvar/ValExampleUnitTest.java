package com.baeldung.lombok.valvar;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import static org.assertj.core.api.Assertions.assertThat;

class ValExampleUnitTest {

    @Test
    void whenUsingValWithString_thenTheAssignedClassIsCorrect() {
        ValExample val = new ValExample();
        assertThat(val.name()).isEqualTo(String.class);
        assertThat(val.age()).isEqualTo(Integer.class);
        assertThat(val.listOf()).isEqualTo(ArrayList.class);
        assertThat(val.mapOf()).isEqualTo(HashMap.class);
        assertThat(val.compoundTypes(true)).isEqualTo(ArrayList.class);
        assertThat(val.compoundTypes(false)).isEqualTo(HashSet.class);
    }

}