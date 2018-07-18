package com.baeldung.reactive.event.streaming;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class WebfluxEventApplication {

    public static void main(String[] args) {

        SpringApplication.run(WebfluxEventApplication.class, args);
        
        WebfluxEventClient client = new WebfluxEventClient();
        client.createWebClient();
        
    }

}
