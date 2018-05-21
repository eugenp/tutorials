/**
 *
 */
package com.baeldung.reactive.stream;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.function.Consumer;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author goobar
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT)
@SuppressWarnings("javadoc")
public class EventClientTest {
    @Autowired
    private EventClient client;

    @Test
    public void given3EventsToConsume_whenConsumeEventsFor4Seconds_Then3EventsAreConsumed() throws Exception {
        // given
        int eventsToConsume = 3;
        Consumer<Event> consumer = mockConsumer();

        // when
        client.consumeEvents(eventsToConsume, consumer);
        Thread.sleep(4_000);

        // then
        verify(consumer, times(3)).accept(any(Event.class));
    }

    /**
     * @return
     */
    @SuppressWarnings("unchecked")
    private Consumer<Event> mockConsumer() {
        return mock(Consumer.class);
    }

}
