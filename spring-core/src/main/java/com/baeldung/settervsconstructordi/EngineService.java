package com.baeldung.settervsconstructordi;

import org.springframework.stereotype.Component;

@Component(value = "engineService")
public class EngineService {
    private String name;

    public String start() {
        return "started";
    }
}