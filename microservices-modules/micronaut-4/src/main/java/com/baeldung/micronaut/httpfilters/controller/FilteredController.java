package com.baeldung.micronaut.httpfilters.controller;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller("/filters-annotations")
public class FilteredController {

    @Get("/endpoint1")
    public HttpResponse<String> endpoint1() {
        return HttpResponse.ok("Endpoint 1");
    }

    @Get("/endpoint2")
    public HttpResponse<String> endpoint2() {
        return HttpResponse.ok("Endpoint 2");
    }
}
