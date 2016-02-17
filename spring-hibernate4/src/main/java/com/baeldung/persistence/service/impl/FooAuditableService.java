package com.baeldung.persistence.service.impl;

import com.baeldung.persistence.dao.common.IAuditOperations;
import com.baeldung.persistence.service.IFooAuditableService;
import com.baeldung.persistence.service.common.AbstractHibernateAuditableService;
import com.baeldung.persistence.dao.IFooAuditableDao;
import com.baeldung.persistence.dao.IFooDao;
import com.baeldung.persistence.dao.common.IOperations;
import com.baeldung.persistence.model.Foo;
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
