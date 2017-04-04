package com.baeldung.injection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FieldDI {
    @Autowired
    private MyService myService;

    public String formatMessage() {
        return myService.getMessage("field injection");
    }
}
