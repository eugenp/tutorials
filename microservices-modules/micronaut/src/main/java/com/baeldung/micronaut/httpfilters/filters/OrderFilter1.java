package com.baeldung.micronaut.httpfilters.filters;

import com.baeldung.micronaut.httpfilters.service.LogService;

import io.micronaut.core.annotation.Order;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.annotation.RequestFilter;
import io.micronaut.http.annotation.ServerFilter;
import io.micronaut.scheduling.TaskExecutors;
import io.micronaut.scheduling.annotation.ExecuteOn;

@Order(100)
@ServerFilter(value="/order-filter")
public class OrderFilter1 {

    private final LogService logService;

    public OrderFilter1(LogService logService) {
        this.logService = logService;
    }

    @RequestFilter
    @ExecuteOn(TaskExecutors.BLOCKING)
    public void filterRequest(HttpRequest<?> request) {
        logService.logOrderFilter(request, "OrderFilter1");
    } 
}