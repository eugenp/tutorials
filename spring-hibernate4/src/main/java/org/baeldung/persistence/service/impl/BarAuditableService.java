package org.baeldung.persistence.service.impl;

import org.baeldung.persistence.dao.IBarAuditableDao;
import org.baeldung.persistence.dao.IBarDao;
import org.baeldung.persistence.dao.common.IAuditOperations;
import org.baeldung.persistence.dao.common.IOperations;
import org.baeldung.persistence.model.Bar;
import org.baeldung.persistence.service.IBarAuditableService;
import org.baeldung.persistence.service.common.AbstractHibernateAuditableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class BarAuditableService extends AbstractHibernateAuditableService<Bar> implements IBarAuditableService {

    @Autowired
    @Qualifier("barHibernateDao")
    private IBarDao dao;

    @Autowired
    @Qualifier("barHibernateAuditableDao")
    private IBarAuditableDao auditDao;

    public BarAuditableService() {
        super();
    }

    // API

    @Override
    protected IOperations<Bar> getDao() {
        return dao;
    }

    @Override
    protected IAuditOperations<Bar> getAuditableDao() {
        return auditDao;
    }

}
