package com.baeldung.injection;

import org.springframework.stereotype.Service;

@Service
public class MyService {
    public String getMessage(String from) {
        return "Hello world from " + from;
    }
}
