package com.baeldung.micronaut.globalexceptionhandler.error.handlers;

import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Error;
import io.micronaut.http.hateoas.JsonError;
import io.micronaut.http.hateoas.Link;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller("/notfound")
public class NotFoundController {

    @Error(status = HttpStatus.NOT_FOUND, global = true)
    public HttpResponse<JsonError> notFound(HttpRequest<?> request) {
        log.info("not found error handled");
        JsonError error = new JsonError("Page Not Found").link(Link.SELF, Link.of(request.getUri()));

        return HttpResponse.<JsonError> notFound()
            .body(error);
    }
}
