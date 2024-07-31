package com.baeldung.spring.cloud.openservicebroker.mail;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MailController {

    @GetMapping("/mail-dashboard/{mailSystemId}")
    public String dashboard(@PathVariable("mailSystemId") String mailSystemId) {
        return "Mail Dashboard - " + mailSystemId;
    }

    @GetMapping("/mail-system/{mailSystemId}")
    public String mailSystem(@PathVariable("mailSystemId") String mailSystemId) {
        return "Mail System - " + mailSystemId;
    }
}
