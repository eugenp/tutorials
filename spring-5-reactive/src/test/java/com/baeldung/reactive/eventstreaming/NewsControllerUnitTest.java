package com.baeldung.reactive.eventstreaming;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.BDDMockito.willReturn;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class NewsControllerUnitTest {

    @Autowired
    private WebTestClient testClient;

    @MockBean
    private NewsRepository newsRepository;

    @Test
    public void whenGetNewsStream_thenCorrectNews() {

        List<NewsEvent> events = new ArrayList<>();

        events.add(new NewsEvent(1, LocalDateTime.now(), "News event #1"));
        events.add(new NewsEvent(2, LocalDateTime.now(), "News event #2"));
        events.add(new NewsEvent(3, LocalDateTime.now(), "News event #3"));

        willReturn(events).given(newsRepository).getEvents();
        testClient.get()
                .uri("/news/stream")
                .exchange()
                .expectStatus()
                .isOk()
                .expectBodyList(NewsEvent.class)
                .hasSize(3)
                .isEqualTo(events);
    }
}
