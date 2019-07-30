package com.baeldung.spring.boot.management.trace;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.boot.actuate.trace.http.HttpTrace;
import org.springframework.boot.actuate.trace.http.HttpTraceRepository;
import org.springframework.stereotype.Repository;

@Repository
public class CustomTraceRepository implements HttpTraceRepository {

    List<HttpTrace> traces = new ArrayList<>(1);

    @Override
    public List<HttpTrace> findAll() {
        synchronized (this.traces) {
            return Collections.unmodifiableList(new ArrayList<>(this.traces));
        }
    }

    @Override
    public void add(HttpTrace trace) {
        if ("GET".equals(trace.getRequest()
            .getMethod())) {
            synchronized (this.traces) {
                traces.clear();
                traces.add(trace);
            }
        }
    }

}
