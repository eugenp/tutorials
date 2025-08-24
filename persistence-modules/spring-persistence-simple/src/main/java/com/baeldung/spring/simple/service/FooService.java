package com.baeldung.spring.simple.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baeldung.spring.simple.model.Foo;
import com.baeldung.spring.simple.repository.IFooDAO;

@Service
public class FooService implements IFooService {

    @Autowired
    private IFooDAO dao;

    @Override
    public Foo create(Foo foo) {
        return dao.save(foo);
    }
}
