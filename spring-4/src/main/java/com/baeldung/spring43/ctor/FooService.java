package com.baeldung.spring43.ctor;

public class FooService {

    private final FooRepository repository;

    public FooService(FooRepository repository) {
        this.repository = repository;
    }

    public FooRepository getRepository() {
        return repository;
    }

}