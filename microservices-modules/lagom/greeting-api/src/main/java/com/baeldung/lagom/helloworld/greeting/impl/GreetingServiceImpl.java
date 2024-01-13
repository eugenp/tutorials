package com.baeldung.lagom.helloworld.greeting.impl;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import com.baeldung.lagom.helloworld.greeting.api.GreetingService;
import com.baeldung.lagom.helloworld.greeting.impl.GreetingCommand.ReceivedGreetingCommand;
import com.baeldung.lagom.helloworld.weather.api.WeatherService;
import com.baeldung.lagom.helloworld.weather.api.WeatherStats;

import com.google.inject.Inject;
import com.lightbend.lagom.javadsl.api.ServiceCall;
import com.lightbend.lagom.javadsl.persistence.PersistentEntityRef;
import com.lightbend.lagom.javadsl.persistence.PersistentEntityRegistry;

import akka.NotUsed;

public class GreetingServiceImpl implements GreetingService {

    private final PersistentEntityRegistry persistentEntityRegistry;
    private final WeatherService weatherService;

    @Inject
    public GreetingServiceImpl(PersistentEntityRegistry persistentEntityRegistry, WeatherService weatherService) {
        this.persistentEntityRegistry = persistentEntityRegistry;
        this.weatherService = weatherService;
        persistentEntityRegistry.register(GreetingEntity.class);
    }

    @Override
    public ServiceCall<NotUsed, String> handleGreetFrom(String user) {
        return request -> {
            // Look up the hello world entity for the given ID.
            PersistentEntityRef<GreetingCommand> ref = persistentEntityRegistry.refFor(GreetingEntity.class, user);
            CompletableFuture<String> greetingResponse = ref.ask(new ReceivedGreetingCommand(user))
              .toCompletableFuture();
            CompletableFuture<WeatherStats> todaysWeatherInfo = 
              (CompletableFuture<WeatherStats>) weatherService.weatherStatsForToday().invoke();
            try {
                return CompletableFuture.completedFuture(greetingResponse.get() + 
                  " Today's weather stats: " + todaysWeatherInfo.get().getMessage());
            } catch (InterruptedException | ExecutionException e) {
                return CompletableFuture.completedFuture("Sorry Some Error at our end, working on it");
            }
        };
    }

}
