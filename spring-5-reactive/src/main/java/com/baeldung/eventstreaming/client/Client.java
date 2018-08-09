package com.baeldung.eventstreaming.client;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.Disposable;

import java.io.IOException;

public class Client {

    private WebClient webClient = WebClient.create("http://localhost:8080");

    public static void main(String[] args) {
        Client eventConsumerWebClient = new Client();
        Disposable disposable = eventConsumerWebClient.eventNoticed();
        try {
            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            disposable.dispose();
        }
    }

    public Disposable eventNoticed() {
        return webClient.get()
                .uri("/events")
                .accept(MediaType.APPLICATION_STREAM_JSON)
                .exchange().subscribe();
    }
}