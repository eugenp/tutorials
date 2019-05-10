package com.baeldung.hexagonal.notification;

import com.baeldung.hexagonal.WeatherAlert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoggingNotifier implements NotifierPort {

    private static final Logger LOG = LoggerFactory.getLogger(LoggingNotifier.class);

    private WeatherAlert latestWeatherAlert;

    @Override
    public void send(WeatherAlert weatherAlert) {
        LOG.info("Bad weather alert! [{}]", weatherAlert);
        this.latestWeatherAlert = weatherAlert;
    }

    @Override
    public WeatherAlert getLatest() {
        return this.latestWeatherAlert;
    }


}
