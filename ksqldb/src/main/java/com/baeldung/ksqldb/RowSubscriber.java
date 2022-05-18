package com.baeldung.ksqldb;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.confluent.ksql.api.client.Row;
import lombok.extern.slf4j.Slf4j;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class RowSubscriber<T> implements Subscriber<Row> {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    private final Class<T> clazz;

    private Subscription subscription;

    public List<T> consumedItems = new ArrayList<>();

    public RowSubscriber(Class<T> clazz) {
        this.clazz = clazz;
    }

    @Override
    public synchronized void onSubscribe(Subscription subscription) {
        log.info("Subscriber is subscribed.");
        this.subscription = subscription;
        subscription.request(1);
    }

    @Override
    public synchronized void onNext(Row row) {
        String jsonString = row.asObject().toJsonString();
        log.info("Row JSON: {}", jsonString);
        try {
            T item = OBJECT_MAPPER.readValue(jsonString, this.clazz);
            log.info("Item: {}", item);
            consumedItems.add(item);
        } catch (JsonProcessingException e) {
            log.error("Unable to parse json", e);
        }

        // Request the next row
        subscription.request(1);
    }

    @Override
    public synchronized void onError(Throwable t) {
        log.error("Received an error", t);
    }

    @Override
    public synchronized void onComplete() {
        log.info("Query has ended.");
    }
}
