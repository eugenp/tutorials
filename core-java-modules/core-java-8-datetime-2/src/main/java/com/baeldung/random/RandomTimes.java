package com.baeldung.random;

import java.time.LocalTime;
import java.util.concurrent.ThreadLocalRandom;

public class RandomTimes {

    public static LocalTime between(LocalTime startTime, LocalTime endTime) {
        int startSeconds = startTime.toSecondOfDay();
        int endSeconds = endTime.toSecondOfDay();
        int randomTime = ThreadLocalRandom.current().nextInt(startSeconds, endSeconds);

        return LocalTime.ofSecondOfDay(randomTime);
    }

    public static LocalTime time() {
        return between(LocalTime.MIN, LocalTime.MAX);
    }
}
