package com.baeldung.lagom.helloworld.greeting.impl;

import com.baeldung.lagom.helloworld.greeting.api.GreetingService;
import com.baeldung.lagom.helloworld.weather.api.WeatherService;

import com.google.inject.AbstractModule;
import com.lightbend.lagom.javadsl.server.ServiceGuiceSupport;

/**
 * The module that binds the GreetingService so that it can be served.
 */
public class GreetingServiceModule extends AbstractModule implements ServiceGuiceSupport {
    @Override
    protected void configure() {
        bindServices(serviceBinding(GreetingService.class, GreetingServiceImpl.class));
        bindClient(WeatherService.class);
    }
}
