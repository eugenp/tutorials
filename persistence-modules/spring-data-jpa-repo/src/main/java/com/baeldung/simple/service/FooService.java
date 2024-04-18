package com.baeldung.simple.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baeldung.simple.entity.Foo;
import com.baeldung.simple.repository.IFooDAO;

@Service
public class FooService implements IFooService {

    @Autowired
    private IFooDAO dao;

    @Override
    public Foo create(Foo foo) {
        return dao.save(foo);
    }
}
