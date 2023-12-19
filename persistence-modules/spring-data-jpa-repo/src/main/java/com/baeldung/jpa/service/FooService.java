package com.baeldung.jpa.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baeldung.jpa.domain.Foo;
import com.baeldung.jpa.repository.IFooDAO;

@Service
public class FooService implements IFooService {

    @Autowired
    private IFooDAO dao;

    @Override
    public Foo create(Foo foo) {
        return dao.save(foo);
    }
}
