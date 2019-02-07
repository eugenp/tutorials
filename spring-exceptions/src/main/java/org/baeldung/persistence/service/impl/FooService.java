package org.baeldung.persistence.service.impl;

import org.baeldung.persistence.common.AbstractService;
import org.baeldung.persistence.common.IOperations;
import org.baeldung.persistence.dao.IFooDao;
import org.baeldung.persistence.model.Foo;
import org.baeldung.persistence.service.IFooService;
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
