package com.baeldung.environmentvariablesfortest;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledForJreRange;
import org.junit.jupiter.api.condition.JRE;

import java.lang.reflect.Field;
import java.util.Map;
import static org.assertj.core.api.Assertions.assertThat;

@EnabledForJreRange(min = JRE.JAVA_8, max = JRE.JAVA_16)
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
