package com.baeldung.micronaut.httpfilters.filters;

import com.baeldung.micronaut.httpfilters.service.LogService;

import io.micronaut.http.HttpRequest;
import io.micronaut.http.annotation.RequestFilter;
import io.micronaut.http.annotation.ServerFilter;
import io.micronaut.scheduling.TaskExecutors;
import io.micronaut.scheduling.annotation.ExecuteOn;

@ServerFilter(value="/hello")
public class HelloFilter {

    private final LogService logService;

    public HelloFilter(LogService logService) {
        this.logService = logService;
    }

    @RequestFilter
    @ExecuteOn(TaskExecutors.BLOCKING)
    public void filterRequest(HttpRequest<?> request) {
        logService.logHelloMessage(request, "Hello message");
    } 
}