package com.baeldung.controller;

import javax.servlet.http.PushBuilder;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class PushController {

    @RequestMapping(value = "/demoWithPush")
    public String demoWithPush(PushBuilder pushBuilder) {
        if (null != pushBuilder) {
            pushBuilder.path("resources/logo.png")
                .addHeader("Content-Type", "image/png")
                .push();
            pushBuilder.path("resources/script.js")
                .addHeader("Content-Type", "text/javascript")
                .push();
            pushBuilder.path("resources/style.css")
                .addHeader("Content-Type", "text/css")
                .push();
        }
        return "demo";
    }

    @RequestMapping(value = "/demoWithoutPush")
    public String demoWithoutPush() {
        return "demo";
    }
}