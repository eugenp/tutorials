package com.baeldung.micronaut.globalexceptionhandler.controller;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller("/probe")
public class ProbesController {

    @Get("/liveness")
    public HttpResponse<String> endpoint1() {
        log.info("endpoint1");

        return HttpResponse.ok();
    }

    @Get("/readiness")
    public HttpResponse<String> endpoint2() {
        log.info("endpoint2");

        throw new UnsupportedOperationException();
    }
}
