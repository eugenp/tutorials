package com.baeldung.injection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ConstructorDI {
    private MyService myService;

    @Autowired
    public ConstructorDI(MyService myService) {
        this.myService = myService;
    }

    public String formatMessage() {
        return myService.getMessage("constructor injection");
    }
}
