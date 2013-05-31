package org.baeldung.persistence.service;

import org.baeldung.persistence.dao.IFooDao;
import org.baeldung.persistence.model.Foo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class FooService {

    @Autowired
    private IFooDao dao;

    public FooService() {
        super();
    }

    // API

    public void create(final Foo entity) {
        dao.create(entity);
    }

}
