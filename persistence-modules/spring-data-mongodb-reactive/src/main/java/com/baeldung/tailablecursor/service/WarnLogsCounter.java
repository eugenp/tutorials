package com.baeldung.tailablecursor.service;

import com.baeldung.tailablecursor.domain.Log;
import com.baeldung.tailablecursor.domain.LogLevel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.core.ReactiveMongoOperations;
import reactor.core.Disposable;
import reactor.core.publisher.Flux;

import javax.annotation.PreDestroy;
import java.util.concurrent.atomic.AtomicInteger;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

@Slf4j
public class WarnLogsCounter implements LogsCounter {

    private static final String LEVEL_FIELD_NAME = "level";

    private final AtomicInteger counter = new AtomicInteger();
    private final Disposable subscription;

    public WarnLogsCounter(ReactiveMongoOperations template) {
        Flux<Log> stream = template.tail(query(where(LEVEL_FIELD_NAME).is(LogLevel.WARN)), Log.class);
        subscription = stream.subscribe(logEntity -> {
          log.warn("WARN log received: " + logEntity);
          counter.incrementAndGet();
        });
    }

    @Override
    public int count() {
        return counter.get();
    }

    @PreDestroy
    public void close() {
        subscription.dispose();
    }
}
