/**
 *
 */
package com.baeldung.reactive.stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.function.Function;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.junit4.SpringRunner;

import reactor.core.publisher.Flux;

/**
 * @author goobar
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT)
@DisableEmbeddedMongo
@SuppressWarnings("javadoc")
public class EventClientTest {
    @Autowired
    private EventClient client;

    @Test
    public void given3EventsToConsume_whenMapEvents_ThenMapExactly3Events() throws Exception {
        // given
        int eventsToConsume = 3;
        Function<Event, String> mapper = mockConsumer();

        // when
        Flux<String> mappedEventsFlux = client.mapEvents(eventsToConsume, mapper);

        // then
        List<String> mappedEvents = mappedEventsFlux.collectList()
            .block();
        assertThat(mappedEvents).hasSize(eventsToConsume);
    }

    /**
     * @return
     */
    @SuppressWarnings("unchecked")
    private Function<Event, String> mockConsumer() {
        Function<Event, String> mock = mock(Function.class);
        when(mock.apply(any(Event.class))).thenReturn("event mock string");
        return mock;
    }

}
