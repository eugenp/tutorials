package com.baeldung.event.streams;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.Duration;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(
    classes = WebFluxEventsApplication.class,
    webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT
)
public class WebFluxEventsApplicationTests {

    private WebClient client;

    @Before
    public void before() {
        client = WebClient.create("http://localhost:8080");
    }

    @Test
    public void when3EventsTakenFromUserController_thenExactly3UsersReceived() {
        List<User> usersList = client.get()
            .uri("/users")
            .accept(MediaType.TEXT_EVENT_STREAM)
            .retrieve()
            .bodyToFlux(User.class)
            .take(3)
            .collectList()
            .block();
        assertThat(usersList).hasSize(3);
    }

    @Test
    public void whenUserControllerRequestedWith4SecondsWindow_thenBetween2and4UsersReceived() {
        List<User> usersList = client.get()
            .uri("/users")
            .accept(MediaType.TEXT_EVENT_STREAM)
            .retrieve()
            .bodyToFlux(User.class)
            .window(Duration.ofSeconds(4))
            .blockFirst()
            .collectList()
            .block();
        assertThat(usersList.size()).isBetween(2, 4);
    }

}
