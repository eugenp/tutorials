package com.baeldung.trace;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

import org.springframework.boot.actuate.web.exchanges.HttpExchange;
import org.springframework.boot.actuate.web.exchanges.HttpExchangeRepository;
import org.springframework.stereotype.Repository;

@Repository
public class CustomTraceRepository implements HttpExchangeRepository {

    AtomicReference<HttpExchange> lastTrace = new AtomicReference<>();

    @Override
    public List<HttpExchange> findAll() {
        return Collections.singletonList(lastTrace.get());
    }

    @Override
    public void add(HttpExchange trace) {
        if ("GET".equals(trace.getRequest()
            .getMethod())) {
            lastTrace.set(trace);
        }
    }

}
