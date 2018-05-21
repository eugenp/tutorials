/**
 *
 */
package com.baeldung.reactive.stream;

import static com.baeldung.reactive.stream.EventFixture.event;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;

/**
 * @author goobar
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@SuppressWarnings("javadoc")
public class EventRouterTest {
    private static final String EVENT_URI = EventRouter.EVENT_URI;
    @Autowired
    private WebTestClient testClient;

    @Test
    public void whenGetEvents_ThenReceive3Events() throws Exception {
        // when
        List<Event> first3Events = testClient.get()
            .uri(EVENT_URI)
            .accept(MediaType.APPLICATION_STREAM_JSON)
            .exchange()
            .returnResult(Event.class)
            .getResponseBody()
            .take(3)
            .collectList()
            .block();

        // then
        assertThat(first3Events).containsExactly(event("event0"), event("event1"), event("event2"));
    }

    @Test
    public void whenGetEvents_ThenReceiveOkStatus() {
        // when
        testClient.get()
            .uri(EVENT_URI)
            .accept(MediaType.APPLICATION_STREAM_JSON)
            .exchange()

            // then
            .expectStatus()
            .isOk();
    }

}
