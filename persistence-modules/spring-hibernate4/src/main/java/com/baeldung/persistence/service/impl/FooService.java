package com.baeldung.persistence.service.impl;

import com.baeldung.persistence.dao.IFooDao;
import com.baeldung.persistence.dao.common.IOperations;
import com.baeldung.persistence.model.Foo;
import com.baeldung.persistence.service.IFooService;
import com.baeldung.persistence.service.common.AbstractHibernateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class FooService extends AbstractHibernateService<Foo> implements IFooService {

    @Autowired
    @Qualifier("fooHibernateDao")
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
