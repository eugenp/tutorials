/**
 *
 */
package com.baeldung.reactive.stream;

import java.util.function.Function;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import reactor.core.publisher.Flux;

/**
 * A simple client to map events from the Webflux endpoint.
 * @author goobar
 *
 */
@Component
public class EventClient {
    private final WebClient webClient;

    /**
     * @param webClient a web client that will connect to the Webflux endpoint
     */
    public EventClient(WebClient webClient) {
        super();
        this.webClient = webClient;
    }

    /**
     * Maps events from original Webflux endpoint into a type determined by given {@code mapper}.
     * @param <T> a type event will mapped into
     * @param eventsToConsume a number of events to consume
     * @param mapper a mapper that will be applied for each of the original events
     * @return the flux of mapped events
     */
    public <T> Flux<T> mapEvents(int eventsToConsume, Function<Event, T> mapper) {
        return webClient.get()
            .uri(EventController.EVENT_URI)
            .accept(MediaType.APPLICATION_STREAM_JSON)
            .retrieve()
            .bodyToFlux(Event.class)
            .log()
            .map(mapper)
            .take(eventsToConsume);
    }
}
