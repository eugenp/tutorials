package com.baeldung.persistence.service.impl;

import com.baeldung.persistence.dao.common.IAuditOperations;
import com.baeldung.persistence.service.common.AbstractHibernateAuditableService;
import com.baeldung.persistence.dao.IBarAuditableDao;
import com.baeldung.persistence.dao.IBarDao;
import com.baeldung.persistence.dao.common.IOperations;
import com.baeldung.persistence.model.Bar;
import com.baeldung.persistence.service.IBarAuditableService;
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
