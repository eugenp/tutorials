package com.baeldung.spring43.scopeannotations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/appointments")
public class TestController {

    @Autowired
    private RequestScopedService requestScopedService;

    @Autowired
    private SessionScopedService sessionScopedService;

    @Autowired
    private ApplicationScopedService applicationScopedService;

    @GetMapping("/request")
    public String getRequestNumber() {
        return Integer.toString(requestScopedService.getInstanceNumber());
    }

    @GetMapping("/session")
    public String getSessionNumber() {
        return Integer.toString(sessionScopedService.getInstanceNumber());
    }

    @GetMapping("/application")
    public String getApplicationNumber() {
        return Integer.toString(applicationScopedService.getInstanceNumber());
    }

}
