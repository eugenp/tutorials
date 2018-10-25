package com.baeldung.reactor;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import reactor.core.publisher.Flux;

public class ItemProducerCreate {

    Logger logger = LoggerFactory.getLogger(NetworTrafficProducerPush.class);

    Consumer<List<String>> listener;

    public void create() {
        Flux<String> articlesFlux = Flux.create((sink) -> {
            ItemProducerCreate.this.listener = (items) -> {
                items.stream()
                    .forEach(article -> sink.next(article));
            };
        });
        articlesFlux.subscribe(ItemProducerCreate.this.logger::info);
    }

    public static void main(String[] args) {
        ItemProducerCreate producer = new ItemProducerCreate();
        producer.create();

        new Thread(new Runnable() {

            @Override
            public void run() {
                List<String> items = new ArrayList<>();
                items.add("Item 1");
                items.add("Item 2");
                items.add("Item 3");
                producer.listener.accept(items);
            }
        }).start();
    }
}
