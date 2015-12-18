package org.baeldung.persistence.service.impl;

import org.baeldung.persistence.dao.IBarDao;
import org.baeldung.persistence.dao.common.IOperations;
import org.baeldung.persistence.model.Bar;
import org.baeldung.persistence.service.IBarService;
import org.baeldung.persistence.service.common.AbstractJpaService;
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