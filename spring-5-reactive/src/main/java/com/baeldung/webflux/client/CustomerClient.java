package com.baeldung.webflux.client;

import com.baeldung.webflux.model.Customer;

import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;


public class CustomerClient {


    public static void main(String[] args) {


        WebClient webClient = WebClient.create("http://localhost:2389");

        webClient.get()
                .uri("/api/customers")
                .accept(MediaType.APPLICATION_STREAM_JSON)
                .retrieve()
                .bodyToFlux(Customer.class)
                .log();

    }

}
