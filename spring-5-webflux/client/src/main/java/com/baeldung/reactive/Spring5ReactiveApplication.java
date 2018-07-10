package com.baeldung.reactive;

import com.baeldung.reactive.model.Foo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.reactive.function.client.WebClient;

@SpringBootApplication
public class Spring5ReactiveApplication{

    public static void main(String[] args) {

        SpringApplication.run(Spring5ReactiveApplication.class, args);

        WebClient client = WebClient.create("http://localhost:8080");

        client.get().uri("/foos")
                .retrieve()
                .bodyToFlux(Foo.class)
                .subscribe(System.out::println);

    }

}
