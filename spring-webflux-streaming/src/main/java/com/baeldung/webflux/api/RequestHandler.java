package com.baeldung.webflux.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import com.baeldung.webflux.model.WeatherEvent;
import com.baeldung.webflux.service.WeatherService;
import reactor.core.publisher.Mono;

@Component
public class RequestHandler {

    @Autowired
    private WeatherService weatherService;

    public Mono<ServerResponse> streamWeather(ServerRequest request) {
        return ServerResponse.ok()
            .contentType(MediaType.TEXT_EVENT_STREAM)
            .body(weatherService.streamWeather(), WeatherEvent.class);
    }

}
