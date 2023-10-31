package com.baeldung.uuid;

import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.DecimalFormat;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class UUIDLongGeneratorUnitTest {
    final static int n = 1000000;
    UUIDLongGenerator uuidLongGenerator = new UUIDLongGenerator();

    @Test
    void whenForeachGenerateLongValue_thenCollisionsCheck() {
        printTableHeader();
        for (Method method : uuidLongGenerator.getClass().getDeclaredMethods()) {
            collisionAndNegativeCheck(method);
        }
    }

    private void printTableHeader() {
        System.out.format("%-30s %-15s %-15s %-15s %-15s%n", "Approach", "collisions", "negatives", "collision", "negative");
        System.out.format("%-30s %-15s %-15s %-15s %-15s%n", "(method name)", "count", "count", "probability", "probability");
        System.out.println("--------------------------------------------------------------------------------------------");
    }

    private void printOutput(String method, int collisionsCount, int negativeCount, double collisionsProbability, double negativeProbability) {
        DecimalFormat decimalFormat = new DecimalFormat("#.#####");
        System.out.format("%-30s %-15s %-15s %-15s %-15s%n", method, collisionsCount, negativeCount, decimalFormat.format(collisionsProbability), decimalFormat.format(negativeProbability));
    }

    private void collisionAndNegativeCheck(Method method) {
        Set<Long> uniqueValues = new HashSet<>();
        AtomicInteger collisions = new AtomicInteger(0);
        AtomicInteger negative = new AtomicInteger(0);

        IntStream.range(0, n).forEach(i -> {
            try {
                long uniqueValue = (long) method.invoke(uuidLongGenerator);
                if (!uniqueValues.add(uniqueValue)) {
                    collisions.incrementAndGet();
                }
                if (uniqueValue < 0) {
                    negative.incrementAndGet();
                }
            } catch (IllegalAccessException | InvocationTargetException e) {
                throw new RuntimeException(e);
            }
        });

        double collisionsProbability = (double) collisions.get() / n;
        double negativeProbability = (double) negative.get() / n;
        printOutput(method.getName(), collisions.get(), negative.get(), collisionsProbability, negativeProbability);

        assertTrue(collisionsProbability <= 0.001); // threshold = 0.001
    }
}
