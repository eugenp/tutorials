package com.baeldung.hexagonal.datafeed;

import com.baeldung.hexagonal.WeatherAlert;

public interface DataFeedPort {
    void trigger(WeatherAlert weatherAlert);
}
