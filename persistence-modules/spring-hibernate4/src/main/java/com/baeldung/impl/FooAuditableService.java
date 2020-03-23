package com.baeldung.port.inbound.impl;

import com.baeldung.port.outbound.common.IAuditOperations;
import com.baeldung.port.inbound.IFooAuditableService;
import com.baeldung.port.inbound.common.AbstractHibernateAuditableService;
import com.baeldung.port.outbound.IFooAuditableDao;
import com.baeldung.port.outbound.IFooDao;
import com.baeldung.port.outbound.common.IOperations;
import com.baeldung.port.model.Foo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class FooAuditableService extends AbstractHibernateAuditableService<Foo> implements IFooAuditableService {

    @Autowired
    @Qualifier("fooHibernateDao")
    private IFooDao dao;

    @Autowired
    @Qualifier("fooHibernateAuditableDao")
    private IFooAuditableDao auditDao;

    public FooAuditableService() {
        super();
    }

    // API

    @Override
    protected IOperations<Foo> getDao() {
        return dao;
    }

    @Override
    protected IAuditOperations<Foo> getAuditableDao() {
        return auditDao;
    }

}
