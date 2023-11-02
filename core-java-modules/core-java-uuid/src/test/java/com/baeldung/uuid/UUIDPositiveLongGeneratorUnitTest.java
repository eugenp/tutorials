package com.baeldung.uuid;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.text.DecimalFormat;
import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

public class UUIDPositiveLongGeneratorUnitTest {
    private final static int NUMBER_OF_CHECKS = 1000000;
    private final static double COLLISION_THRESHOLD = 0.001;
    private final UUIDPositiveLongGenerator uuidLongGenerator = new UUIDPositiveLongGenerator();
    private final Logger logger = LoggerFactory.getLogger(UUIDPositiveLongGeneratorUnitTest.class);

    @Test
    void whenForeachGenerateLongValue_thenCollisionsCheck() throws Exception {
        printTableHeader();
        for (Method method : uuidLongGenerator.getClass().getDeclaredMethods()) {
            collisionCheck(method);
        }
    }


    private void printTableHeader() {
        logger.info(String.format("%-30s %-15s %-15s", "Approach(method name)", "collisions", "probability"));
        logger.info("-----------------------------------------------------------------------");
    }

    private void printOutput(String method, int collisionsCount, double collisionsProbability) {
        DecimalFormat decimalFormat = new DecimalFormat("#.#####");
        logger.info(String.format("%-30s %-15s %-15s", method, collisionsCount, decimalFormat.format(collisionsProbability)));
    }


    private void collisionCheck(Method method) throws Exception {
        Set<Long> uniqueValues = new HashSet<>();
        int collisions = 0;
        for (int i = 0; i < NUMBER_OF_CHECKS; i++) {
            long uniqueValue = (long) method.invoke(uuidLongGenerator);
            assertThat(uniqueValue).isPositive();
            if (!uniqueValues.add(uniqueValue)) {
                collisions++;
            }
        }
        double collisionsProbability = (double) collisions / NUMBER_OF_CHECKS;
        printOutput(method.getName(), collisions, collisionsProbability);
        assertThat(collisionsProbability).isLessThan(COLLISION_THRESHOLD);
    }
}
