package com.baeldung.webfluxdemo;

import com.baeldung.webfluxdemo.model.Event;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import static org.springframework.http.MediaType.APPLICATION_JSON;

@RunWith(SpringRunner.class)
@SpringBootTest
public class WebfluxDemoApplicationTests {

    private WebClient client = WebClient.create("http://localhost:8080");

    @Test
    public void contextLoads() {
        try {
            int i = 1;
            String code = "Event No: ";
            while (true) {
                Event event = new Event(i, code+i);
                final Mono<ClientResponse> postResponse =
                        client
                                .post()
                                .uri("/events")
                                .body(Mono.just(event), Event.class)
                                .accept(APPLICATION_JSON)
                                .exchange();
                postResponse
                        .map(ClientResponse::statusCode)
                        .subscribe(status -> System.out.println("POST: " + status.getReasonPhrase()));
                Thread.sleep(1000);
                i = i+1;
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
