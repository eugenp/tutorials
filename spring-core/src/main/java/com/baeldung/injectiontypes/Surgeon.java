package com.baeldung.injectiontypes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Surgeon {
    @Autowired
    private Hospital hospital;

    public Surgeon() {
        //TODO
    }

    public Hospital getHospital() {
        return hospital;
    }
}