package org.baeldung.lagom.helloworld.weather.impl;

import java.util.concurrent.CompletableFuture;

import org.baeldung.lagom.helloworld.weather.api.WeatherService;
import org.baeldung.lagom.helloworld.weather.api.WeatherStats;

import com.lightbend.lagom.javadsl.api.ServiceCall;

import akka.NotUsed;

public class WeatherServiceImpl implements WeatherService {

    @Override
    public ServiceCall<NotUsed, WeatherStats> weatherStatsForToday() {
        return (req) -> CompletableFuture.completedFuture(WeatherStats.forToday());
    }

}
