package com.baeldung.reactive.client;

import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.Disposable;

public class EmailWebClient {

    public Disposable getEmails() {
        WebClient webClient = WebClient.builder()
            .baseUrl("http://localhost:8080")
            .build();

        return webClient.get()
            .uri("/emails")
            .accept(MediaType.APPLICATION_STREAM_JSON)
            .exchange()
            .subscribe(email -> System.out.println("Email: " + email));
    }

    public static void main(String[] args) {
        EmailWebClient vehiclesWebClient = new EmailWebClient();
        Disposable disposable = vehiclesWebClient.getEmails();
        try {
            // sleep for 5 minutes
            Thread.sleep(300000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            disposable.dispose();
        }
    }
}
