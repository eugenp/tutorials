package org.baeldung.spring.persistence.service.impl;

import org.baeldung.spring.persistence.dao.IFooDao;
import org.baeldung.spring.persistence.dao.common.IOperations;
import org.baeldung.spring.persistence.model.Foo;
import org.baeldung.spring.persistence.service.IFooService;
import org.baeldung.spring.persistence.service.common.AbstractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FooService extends AbstractService<Foo> implements IFooService {

    @Autowired
    private IFooDao dao;

    public FooService() {
        super();
    }

    // API

    @Override
    protected IOperations<Foo> getDao() {
        return dao;
    }

}
