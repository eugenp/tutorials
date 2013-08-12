package org.baeldung.ex.mappingexception.cause1.persistence.service;

import org.baeldung.ex.mappingexception.cause1.persistence.dao.IFooDao;
import org.baeldung.ex.mappingexception.cause1.persistence.model.Foo;
import org.baeldung.persistence.common.AbstractService;
import org.baeldung.persistence.common.IOperations;
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
