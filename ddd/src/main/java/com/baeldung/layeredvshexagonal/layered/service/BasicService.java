package com.baeldung.layeredvshexagonal.layered.service;

import com.baeldung.layeredvshexagonal.layered.repository.Repository;

public class BasicService implements Service {

    private final Repository repository;

    public BasicService(final Repository repository) {
        this.repository = repository;
    }
}
