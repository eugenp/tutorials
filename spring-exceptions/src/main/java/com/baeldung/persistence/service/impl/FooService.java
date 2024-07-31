package com.baeldung.persistence.service.impl;

import com.baeldung.persistence.common.AbstractService;
import com.baeldung.persistence.common.IOperations;
import com.baeldung.persistence.service.IFooService;
import com.baeldung.persistence.dao.IFooDao;
import com.baeldung.persistence.model.Foo;
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
