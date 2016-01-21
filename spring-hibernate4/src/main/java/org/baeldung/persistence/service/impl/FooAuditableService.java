package org.baeldung.persistence.service.impl;

import org.baeldung.persistence.dao.IFooAuditableDao;
import org.baeldung.persistence.dao.IFooDao;
import org.baeldung.persistence.dao.common.IAuditOperations;
import org.baeldung.persistence.dao.common.IOperations;
import org.baeldung.persistence.model.Foo;
import org.baeldung.persistence.service.IFooAuditableService;
import org.baeldung.persistence.service.common.AbstractHibernateAuditableService;
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
