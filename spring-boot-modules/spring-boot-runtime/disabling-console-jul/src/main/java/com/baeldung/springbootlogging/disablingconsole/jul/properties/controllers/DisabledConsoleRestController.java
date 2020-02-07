package com.baeldung.springbootlogging.disablingconsole.jul.properties.controllers;

import java.time.LocalTime;
import java.util.logging.Logger;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DisabledConsoleRestController {

    private static final Logger LOG = Logger.getLogger(DisabledConsoleRestController.class.getName());

    @GetMapping("/disabled-console-jul-properties")
    public String logTime() {
        LOG.info("Current time: " + LocalTime.now());
        return "finished!";
    }
}
