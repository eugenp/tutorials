package com.baeldung.uuid;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

public class UUIDPositiveLongGeneratorUnitTest {

    //private final UUIDPositiveLongGenerator uuidLongGenerator = new UUIDPositiveLongGenerator();

    Set<Long> uniqueValues = new HashSet<>();

    @Test
    void whenForeachGenerateLongValue_thenCollisionsCheck() throws Exception {
        UUIDPositiveLongGenerator uuidLongGenerator = new UUIDPositiveLongGenerator();
        for (Method method : uuidLongGenerator.getClass().getDeclaredMethods()) {
            long uniqueValue;
            do uniqueValue = (long) method.invoke(uuidLongGenerator); while (!isUnique(uniqueValue));
            assertThat(uniqueValue).isPositive();
        }
    }

    private boolean isUnique(long value) {
        // Implement uniqueness checking logic, for example, by checking in the database
        return uniqueValues.add(value);
    }

}
