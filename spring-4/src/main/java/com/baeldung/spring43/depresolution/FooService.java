package com.baeldung.spring43.depresolution;

import org.springframework.beans.factory.ObjectProvider;
import org.springframework.stereotype.Service;

@Service
public class FooService {

    private final FooRepository repository;

    public FooService(ObjectProvider<FooRepository> repositoryProvider) {
        this.repository = repositoryProvider.getIfUnique();
    }

    public FooRepository getRepository() {
        return repository;
    }
}
