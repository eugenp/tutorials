package com.baeldung.logging.logback;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ForecastLogger {

    private static Logger logger = LoggerFactory.getLogger(ForecastLogger.class);

    public static void logWeatherConditions(String conditions) {
        logger.info("Today's weather conditions: {}", conditions);
    }

    public static void logWeatherEvents(String events) {
        logger.warn("Today's weather events: {}", events);
    }

    public static void logWeatherSystemError(String message) {
        logger.error("Weather system error occured: {}", message);
    }

}
