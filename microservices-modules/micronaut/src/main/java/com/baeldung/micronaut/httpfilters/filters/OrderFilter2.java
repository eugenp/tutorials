package com.baeldung.micronaut.httpfilters.filters;

import com.baeldung.micronaut.httpfilters.service.LogService;

import io.micronaut.core.order.Ordered;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.annotation.RequestFilter;
import io.micronaut.http.annotation.ServerFilter;
import io.micronaut.scheduling.TaskExecutors;
import io.micronaut.scheduling.annotation.ExecuteOn;

@ServerFilter(value = "/order-filter")
public class OrderFilter2 implements Ordered {

    private final LogService logService;

    public OrderFilter2(LogService logService) {
        this.logService = logService;
    }

    @RequestFilter
    @ExecuteOn(TaskExecutors.BLOCKING)
    public void filterRequest(HttpRequest<?> request) {
        logService.logOrderFilter(request, "OrderFilter2");
    }

    @Override
    public int getOrder() {
        return 99;
    }
}
