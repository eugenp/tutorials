package com.baeldung.lombok.valvar;

import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.assertj.core.api.Assertions.assertThat;

class VarExampleUnitTest {

    @Test
    void whenUsingVarWithString_thenTheAssignedClassIsCorrect() {
        VarExample varExample = new VarExample();
        assertThat(varExample.name()).isEqualTo("newName");
        assertThat(varExample.age()).isEqualTo(35);
        assertThat("Day 2").isIn(varExample.listOf());
        assertThat(varExample.mapOf()).containsValue("Book 3");
    }

}