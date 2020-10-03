package com.baeldung.spring.dao.generics;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FooService implements IFooService {

    IGenericDao<Foo> dao;

    @Autowired
    public void setDao(IGenericDao<Foo> daoToSet) {
        dao = daoToSet;
        dao.setClazz(Foo.class);
    }

    @Override
    public Foo retrieveByName(String name) {
        return null;
    }
}
