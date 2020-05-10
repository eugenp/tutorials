package com.baeldung.lagom.helloworld.weather.api;

import static com.lightbend.lagom.javadsl.api.Service.named;
import static com.lightbend.lagom.javadsl.api.Service.*;

import com.lightbend.lagom.javadsl.api.Descriptor;
import com.lightbend.lagom.javadsl.api.Service;
import com.lightbend.lagom.javadsl.api.ServiceCall;
import com.lightbend.lagom.javadsl.api.transport.Method;

import akka.NotUsed;

/**
 * WeatherService Interface.
 */
public interface WeatherService extends Service {

    // Fetch Today's Weather Stats service call
    public ServiceCall<NotUsed, WeatherStats> weatherStatsForToday();

    @Override
    default Descriptor descriptor() {
      return named("weatherservice").withCalls(
          restCall(Method.GET, "/api/weather", this::weatherStatsForToday)
        ).withAutoAcl(true);
    }

}