package com.baeldung.persistence.service.impl;

import com.baeldung.persistence.dao.IBarDao;
import com.baeldung.persistence.dao.common.IOperations;
import com.baeldung.persistence.model.Bar;
import com.baeldung.persistence.service.IBarService;
import com.baeldung.persistence.service.common.AbstractHibernateService;
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
