package com.baeldung.lagom.helloworld.weather.api;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public enum WeatherStats {

    STATS_RAINY("Going to Rain, Take Umbrella"), STATS_HUMID("Going to be very humid, Take Water");

    private final String message;

    private static final List<WeatherStats> VALUES = Collections.unmodifiableList(Arrays.asList(values()));

    private static final int SIZE = VALUES.size();

    private static final Random RANDOM = new Random();

    WeatherStats(String msg) {
        this.message = msg;
    }

    public static WeatherStats forToday() {
        return VALUES.get(RANDOM.nextInt(SIZE));
    }

    public String getMessage() {
        return message;
    }

}
