package com.baeldung.settervsconstructordi;

import org.springframework.stereotype.Component;

@Component
public class EngineService {
    private String name;

    public String start() {
        return "started";
    }
}