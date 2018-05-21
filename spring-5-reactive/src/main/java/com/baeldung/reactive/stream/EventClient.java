/**
 *
 */
package com.baeldung.reactive.stream;

import java.util.function.Consumer;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * A simple client to consume the Webflux endpoint.
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
     * @param eventsToConsume a number of events to consume
     * @param consumer a consumer that will consume each event separately
     */
    public void consumeEvents(int eventsToConsume, Consumer<Event> consumer) {
        webClient.get()
            .uri(EventController.EVENT_URI)
            .accept(MediaType.APPLICATION_STREAM_JSON)
            .retrieve()
            .bodyToFlux(Event.class)
            .log()
            .take(eventsToConsume)
            .subscribe(consumer);
    }
}
