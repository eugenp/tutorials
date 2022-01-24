package com.baeldung.layeredvshexagonal.hexagonal.service;

public class BasicService implements Service {

    private final Repository repository;

    public BasicService(final Repository repository) {
        this.repository = repository;
    }
}
