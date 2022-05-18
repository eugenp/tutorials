package com.baeldung.tailablecursor.service;

import com.baeldung.tailablecursor.domain.Log;
import com.baeldung.tailablecursor.domain.LogLevel;
import com.baeldung.tailablecursor.repository.LogsRepository;
import lombok.extern.slf4j.Slf4j;
import reactor.core.Disposable;
import reactor.core.publisher.Flux;

import javax.annotation.PreDestroy;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
public class InfoLogsCounter implements LogsCounter {

    private final AtomicInteger counter = new AtomicInteger();
    private final Disposable subscription;

    public InfoLogsCounter(LogsRepository repository) {
        Flux<Log> stream = repository.findByLevel(LogLevel.INFO);
        this.subscription = stream.subscribe(logEntity -> {
          log.info("INFO log received: " + logEntity);
          counter.incrementAndGet();
        });
    }

    @Override
    public int count() {
        return this.counter.get();
    }

    @PreDestroy
    public void close() {
        this.subscription.dispose();
    }
}
