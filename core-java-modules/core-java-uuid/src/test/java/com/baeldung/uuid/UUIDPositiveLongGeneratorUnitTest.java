package com.baeldung.uuid;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

public class UUIDPositiveLongGeneratorUnitTest {

    private final UUIDPositiveLongGenerator uuidLongGenerator = new UUIDPositiveLongGenerator();

    private final Set<Long> uniqueValues = new HashSet<>();

    @Test
    void whenForeachMethods_thenRetryWhileNotUnique() throws Exception {
        for (Method method : uuidLongGenerator.getClass().getDeclaredMethods()) {
            long uniqueValue;
            do uniqueValue = (long) method.invoke(uuidLongGenerator); while (!isUnique(uniqueValue));
            assertThat(uniqueValue).isPositive();
        }
    }

    @Test
    void whenGivenLongValue_thenCheckUniqueness() {
        long uniqueValue = generateUniqueLong();
        assertThat(uniqueValue).isPositive();
    }

    private long generateUniqueLong() {
        long uniqueValue;
        do uniqueValue = uuidLongGenerator.combineBitwise(); while (!isUnique(uniqueValue));
        return uniqueValue;
    }

    private boolean isUnique(long value) {
        // Implement uniqueness checking logic, for example, by checking in the database
        return uniqueValues.add(value);
    }

}
