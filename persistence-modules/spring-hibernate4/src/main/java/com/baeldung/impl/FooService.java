package com.baeldung.port.inbound.impl;

import com.baeldung.port.outbound.IFooDao;
import com.baeldung.port.outbound.common.IOperations;
import com.baeldung.port.model.Foo;
import com.baeldung.port.inbound.IFooService;
import com.baeldung.port.inbound.common.AbstractHibernateService;
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
