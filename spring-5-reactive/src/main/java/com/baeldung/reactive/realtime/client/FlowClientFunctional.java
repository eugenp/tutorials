package com.baeldung.reactive.realtime.client;

import com.baeldung.reactive.realtime.model.Event;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.Disposable;

public class FlowClientFunctional {

    Logger logger = LoggerFactory.getLogger(FlowClientFunctional.class);

    private WebClient webClient = WebClient.builder().baseUrl("http://localhost:8080").build();

    public Disposable handleFlow(){
        return webClient.get()
                .uri("/realtime-event/flow-functional")
                .accept(MediaType.APPLICATION_STREAM_JSON)
                .retrieve()
                .bodyToFlux(Event.class)
                .subscribe(event -> {
                    logger.info("Functional Event received [id="+event.getId()+"]");
                });
    }

    public static void main(String[] args) {
        FlowClientFunctional client = new FlowClientFunctional();
        Disposable disposable = client.handleFlow();
        try {
            Thread.sleep(15000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            disposable.dispose();
        }
    }
}
