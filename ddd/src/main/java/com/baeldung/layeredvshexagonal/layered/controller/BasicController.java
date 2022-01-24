package com.baeldung.layeredvshexagonal.layered.controller;

import com.baeldung.layeredvshexagonal.layered.service.Service;

public class BasicController implements Controller {

    private final Service service;

    public BasicController(final Service service) {
        this.service = service;
    }
}
