package com.baeldung.environmentvariablesfortest;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.Map;
import static org.assertj.core.api.Assertions.assertThat;

// This test can only work well in Java 15 and below
class EnvironmentVariablesSetDirectlyUnitTest {
    @BeforeAll
    static void beforeAll() throws Exception {
        Class<?> classOfMap = System.getenv().getClass();
        Field field = classOfMap.getDeclaredField("m");
        field.setAccessible(true);
        Map<String, String> writeableEnvironmentVariables = (Map<String, String>)field.get(System.getenv());

        writeableEnvironmentVariables.put("baeldung", "has set an environment variable");
    }

    @Test
    void givenEnvironmentVariableWasSet_thenCanReadIt() {
        assertThat(System.getenv("baeldung")).isEqualTo("has set an environment variable");
    }

    @Test
    void givenEnvironmentVariableSetBySurefire_thenCanReadIt() {
        assertThat(System.getenv("SET_BY_SUREFIRE")).isEqualTo("YES");
    }
}
