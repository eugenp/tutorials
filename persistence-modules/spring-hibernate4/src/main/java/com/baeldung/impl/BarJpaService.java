package com.baeldung.port.inbound.impl;

import com.baeldung.port.outbound.IBarDao;
import com.baeldung.port.outbound.common.IOperations;
import com.baeldung.port.model.Bar;
import com.baeldung.port.inbound.IBarService;
import com.baeldung.port.inbound.common.AbstractJpaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class BarJpaService extends AbstractJpaService<Bar> implements IBarService {

    @Autowired
    @Qualifier("barJpaDao")
    private IBarDao dao;

    public BarJpaService() {
        super();
    }

    // API

    @Override
    protected IOperations<Bar> getDao() {
        return dao;
    }

}