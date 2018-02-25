package com.baeldung.ditypes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MainComponent {

    private final SmsService smsService;

    @Autowired
    public MainComponent(SmsService smsService) {
        this.smsService = smsService;
    }
}
