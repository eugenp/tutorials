package com.baeldung.event.streaming.client.webclient;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.ExchangeFilterFunctions;
import org.springframework.web.reactive.function.client.WebClient;

import com.baeldung.event.streaming.model.Transaction;

@Component
public class EventStreamingWebClient {

    @PostConstruct
    public void initialise() {
        client = WebClient.builder()
            .baseUrl(baseUrl)
            .filter(ExchangeFilterFunctions.basicAuthentication(username, password))
            .build();
    }

    public void listen() {
        client.get()
            .uri(transactionsEndpoint)
            .accept(MediaType.APPLICATION_STREAM_JSON)
            .exchange()
            .flatMapMany(response -> response.bodyToFlux(Transaction.class))
            .subscribe(transaction -> System.out.println("Transaction Received: " + transaction), err -> System.out.println("Error in Transaction: " + err), () -> System.out.println("Transactions Stopped."));
    }

    @Value("${api.base.url.transactions}")
    private String baseUrl;

    @Value("${api.endpoint.all.transactions}")
    private String transactionsEndpoint;

    @Value("${api.password}")
    private String password;

    @Value("${api.username}")
    private String username;

    private WebClient client;

}
