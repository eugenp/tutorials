package com.baeldung.layeredvshexagonal.hexagonal.controller;

import com.baeldung.layeredvshexagonal.hexagonal.service.Service;

public class BasicController implements Controller {

    private final Service service;

    public BasicController(final Service service) {
        this.service = service;
    }
}
