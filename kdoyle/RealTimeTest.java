package com.baeldung.reactive.kdoyle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class RealTimeTest {

    @Autowired
    WebClient client;

    public void read(){
        client.get().uri("/read")
                .accept(MediaType.TEXT_EVENT_STREAM)
                .retrieve()
                .bodyToFlux(ReadingBook.class)
                .subscribe(System.out::println);
    }
}
