package com.baeldung.micronaut.httpfilters.controller;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Head;

@Controller
public class AppController {

    @Get(uri = "/hello", produces = "text/plain")
    String hello() {

        return "hello there!";
    }

    @Get(uri = "/order-filter", produces = "text/plain")
    String orderfilter() {

        return "calling order filters";
    }

    @Get(uris = { "/pattern", "/pattern1", "/pattern2", "/pattern3",
            "/pattern4", "/pattern5" }, produces = "text/plain")
    String pattern() {

        return "calling pattern filters";
    }

    @Head(uri = "/pattern-m")
    HttpResponse<?> methods() {

        return HttpResponse.ok()
                .header("X-TEST-HEAD", "HEAD request processed");
    }

    @Get(uri = "/", produces = "text/html")
    String home() {

        return HttpResponse.ok("home page").body();
    }

    @Get(uri = "/status", produces = "text/plain")
    HttpResponse<?> status() {

        return HttpResponse.ok();
    }
}