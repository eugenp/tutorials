package com.baeldung.uuid;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.text.DecimalFormat;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;

public class UUIDPositiveLongGeneratorUnitTest {
    private final static int NUMBER_OF_CHECKS = 1000000;
    private final static double COLLISION_THRESHOLD = 0.001;
    private final UUIDPositiveLongGenerator uuidLongGenerator = new UUIDPositiveLongGenerator();
    private final Logger logger = LoggerFactory.getLogger(UUIDPositiveLongGeneratorUnitTest.class);

    List<Long> times = new ArrayList<>();

    @Test
    void whenForeachGenerateLongValue_thenCollisionsCheck() throws Exception {
        times.clear();
        printTableHeader();
        for (Method method : uuidLongGenerator.getClass().getDeclaredMethods()) {
            collisionCheck(method);
        }
    }

    private void printTableHeader() {
        logger.info(String.format("%-25s %15s %15s %15s", "Approach(method name)", "collisions", "probability","Testing Time"));
        logger.info("--------------------------------------------------------------------------------");
    }

    private void printOutput(String method, int collisionsCount, double collisionsProbability, String time) {
        DecimalFormat decimalFormat = new DecimalFormat("#.#####");
        logger.info(String.format("%-25s %15s %15s %15s", method, collisionsCount, decimalFormat.format(collisionsProbability),time));
    }

    private void collisionCheck(Method method) throws Exception {
        long start = System.currentTimeMillis();
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
        long end = System.currentTimeMillis();
        String time = convertMillisToTime(end - start);
        printOutput(method.getName(), collisions, collisionsProbability, time);
        assertThat(collisionsProbability).isLessThan(COLLISION_THRESHOLD);

    }

    private String convertMillisToTime(long currentTimeMillis) {

        long totalMilliseconds = currentTimeMillis % 1000;
        times.add(totalMilliseconds);
        long totalSeconds = (currentTimeMillis / 1000) % 60;
        long totalMinutes = (currentTimeMillis / 60000) % 60;
        long hours = (currentTimeMillis / 3600000);
        StringJoiner stringBuffer = new StringJoiner("");

        if (hours > 0){
            stringBuffer.add(hours+"h ");
        }
        if (totalMinutes > 0){
            stringBuffer.add(totalMinutes+" m ");
        }
        if (totalSeconds > 0){
            stringBuffer.add(totalSeconds+" s ");
        }
        if (totalMilliseconds > 0){
            stringBuffer.add(totalMilliseconds+" ms");
        }
        return stringBuffer.toString();
    }
}
