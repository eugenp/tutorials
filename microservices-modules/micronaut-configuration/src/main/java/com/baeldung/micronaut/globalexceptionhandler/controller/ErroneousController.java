package com.baeldung.micronaut.globalexceptionhandler.controller;

import com.baeldung.micronaut.globalexceptionhandler.error.CustomChildException;
import com.baeldung.micronaut.globalexceptionhandler.error.CustomException;

import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Error;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Header;
import io.micronaut.http.hateoas.JsonError;
import io.micronaut.http.hateoas.Link;
import jakarta.annotation.Nullable;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller("/erroneous-endpoint")
public class ErroneousController {

    @Get("/not-found-error")
    public HttpResponse<String> endpoint1() {
        log.info("endpoint1");

        return HttpResponse.notFound();
    }

    @Get("/internal-server-error")
    public HttpResponse<String> endpoint2(@Nullable @Header("skip-error") String isErrorSkipped) {
        log.info("endpoint2");
        if (isErrorSkipped == null) {
            throw new RuntimeException("something went wrong");
        }

        return HttpResponse.ok("Endpoint 2");
    }

    @Get("/custom-error")
    public HttpResponse<String> endpoint3(@Nullable @Header("skip-error") String isErrorSkipped) {
        log.info("endpoint3");
        if (isErrorSkipped == null) {
            throw new CustomException("something else went wrong");
        }

        return HttpResponse.ok("Endpoint 3");
    }

    @Get("/custom-child-error")
    public HttpResponse<String> endpoint4(@Nullable @Header("skip-error") String isErrorSkipped) {
        log.info("endpoint4");
        if (isErrorSkipped == null) {
            throw new CustomChildException("something else went wrong");
        }

        return HttpResponse.ok("Endpoint 4");
    }

    @Error(exception = UnsupportedOperationException.class)
    public HttpResponse<JsonError> unsupportedOperationExceptions(HttpRequest<?> request) {
        log.info("Unsupported Operation Exception handled");
        JsonError error = new JsonError("Unsupported Operation").link(Link.SELF, Link.of(request.getUri()));

        return HttpResponse.<JsonError> notFound()
            .body(error);
    }

    @Get("/unsupported-operation")
    public HttpResponse<String> endpoint5() {
        log.info("endpoint5");
        throw new UnsupportedOperationException();
    }
}
