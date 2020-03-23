package com.baeldung.port.inbound.impl;

import com.baeldung.port.outbound.IBarDao;
import com.baeldung.port.outbound.common.IOperations;
import com.baeldung.port.model.Bar;
import com.baeldung.port.inbound.IBarService;
import com.baeldung.port.inbound.common.AbstractHibernateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class BarService extends AbstractHibernateService<Bar> implements IBarService {

    @Autowired
    @Qualifier("barHibernateDao")
    private IBarDao dao;

    public BarService() {
        super();
    }

    // API

    @Override
    protected IOperations<Bar> getDao() {
        return dao;
    }

}
