package com.baeldung.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FooService implements IFooService {
    @Autowired
    private IFooDAO dao;

    @Override
    public Foo create(Foo foo) {
        return dao.save(foo);
    }
}
