package com.baeldung.port.inbound.impl;

import com.baeldung.port.outbound.common.IAuditOperations;
import com.baeldung.port.inbound.common.AbstractHibernateAuditableService;
import com.baeldung.port.outbound.IBarAuditableDao;
import com.baeldung.port.outbound.IBarDao;
import com.baeldung.port.outbound.common.IOperations;
import com.baeldung.port.model.Bar;
import com.baeldung.port.inbound.IBarAuditableService;
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
