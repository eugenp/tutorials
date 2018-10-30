package com.baeldung.reactor;

import reactor.core.publisher.Flux;

import java.util.List;
import java.util.function.Consumer;

public class ItemProducerCreate {

    Consumer<List<String>> listener;

    public Flux<String> create() {
        Flux<String> articlesFlux = Flux.create((sink) -> {
            ItemProducerCreate.this.listener = (items) -> {
                items.stream()
                    .forEach(article -> sink.next(article));
            };
        });
        return articlesFlux;
    }

}
