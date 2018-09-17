package com.baeldung.web.reactive.client;

import com.baeldung.reactive.model.Event;
import org.slf4j.Logger;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.Disposable;

public class SpringWebfluxClientApplication {
    private static Logger log = org.slf4j.LoggerFactory.getLogger(SpringWebfluxClientApplication.class);

    public static void main (String[] args) throws InterruptedException {
        WebClient webClient = WebClient.create("http://localhost:8080/api");

        Disposable disposable = webClient.get()
                .uri("/events")
                .retrieve()
                .bodyToFlux(Event.class)
                .subscribe(event -> log.info(event.toString()));

        Thread.sleep(10000);

        disposable.dispose();
    }

}
