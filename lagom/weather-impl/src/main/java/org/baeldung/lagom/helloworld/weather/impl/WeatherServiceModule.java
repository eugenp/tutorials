package org.baeldung.lagom.helloworld.weather.impl;

import com.google.inject.AbstractModule;
import com.lightbend.lagom.javadsl.server.ServiceGuiceSupport;
import org.baeldung.lagom.helloworld.weather.api.WeatherService;

/**
 * The module that binds the GreetingService so that it can be served.
 */
public class WeatherServiceModule extends AbstractModule implements ServiceGuiceSupport {
    @Override
    protected void configure() {
        bindServices(serviceBinding(WeatherService.class, WeatherServiceImpl.class));
    }
}
