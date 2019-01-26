package com.baeldung.spring.controller.push;

import javax.servlet.http.PushBuilder;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PushController {

    @GetMapping(path = "/demoWithPush")
    public String demoWithPush(PushBuilder pushBuilder) {
        if (null != pushBuilder) {
            pushBuilder.path("resources/logo.png")
                .addHeader("Content-Type", "image/png")
                .push();
        }
        return "demo";
    }

    @GetMapping(path = "/demoWithoutPush")
    public String demoWithoutPush() {
        return "demo";
    }
}