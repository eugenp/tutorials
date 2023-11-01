package com.baeldung.uuid;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.DecimalFormat;
import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

public class UUIDPositiveLongGeneratorUnitTest {
    private final static int n = 1000000;
    private final static double COLLISION_THRESHOLD = 0.001;
    private final UUIDPositiveLongGenerator uuidLongGenerator = new UUIDPositiveLongGenerator();
    private final Logger logger = LoggerFactory.getLogger(UUIDPositiveLongGeneratorUnitTest.class);

    @Test
    void whenForeachGenerateLongValue_thenCollisionsCheck() throws InvocationTargetException, IllegalAccessException {
        printTableHeader();
        for (Method method : uuidLongGenerator.getClass().getDeclaredMethods()) {
            collisionCheck(method);
        }
    }


    private void printTableHeader() {
        logger.info(String.format("%-30s %-15s %-15s %-15s %-15s", "Approach", "collisions", "negatives", "collision", "negative"));
        logger.info(String.format("%-30s %-15s %-15s %-15s %-15s", "(method name)", "count", "count", "probability", "probability"));
        logger.info("--------------------------------------------------------------------------------------------");
    }

    private void printOutput(String method, int collisionsCount, int negativeCount, double collisionsProbability, double negativeProbability) {
        DecimalFormat decimalFormat = new DecimalFormat("#.#####");
        logger.info(String.format("%-30s %-15s %-15s %-15s %-15s", method, collisionsCount, negativeCount, decimalFormat.format(collisionsProbability), decimalFormat.format(negativeProbability)));
    }


    private void collisionCheck(Method method) throws InvocationTargetException, IllegalAccessException {
        Set<Long> uniqueValues = new HashSet<>();
        int collisions = 0;
        int negative = 0;
        for (int i = 0; i < n; i++) {
            long uniqueValue = (long) method.invoke(uuidLongGenerator);
            if (!uniqueValues.add(uniqueValue)) {
                collisions++;
            }
            if (uniqueValue < 0) {
                negative++;
            }
        }
        double collisionsProbability = (double) collisions / n;
        double negativeProbability = (double) negative / n;
        printOutput(method.getName(), collisions, negative, collisionsProbability, negativeProbability);
        assertThat(collisionsProbability).isLessThan(COLLISION_THRESHOLD);
    }
}
