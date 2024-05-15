package com.baeldung.lagom.helloworld.weather.impl;

import com.baeldung.lagom.helloworld.weather.api.WeatherService;

import com.google.inject.AbstractModule;
import com.lightbend.lagom.javadsl.server.ServiceGuiceSupport;

/**
 * The module that binds the GreetingService so that it can be served.
 */
public class WeatherServiceModule extends AbstractModule implements ServiceGuiceSupport {
    @Override
    protected void configure() {
        bindServices(serviceBinding(WeatherService.class, WeatherServiceImpl.class));
    }
}
