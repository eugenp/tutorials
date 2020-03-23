package com.baeldung.port.inbound.impl;

import com.baeldung.port.model.Child;
import com.baeldung.port.inbound.IChildService;
import com.baeldung.port.outbound.IChildDao;
import com.baeldung.port.outbound.common.IOperations;
import com.baeldung.port.inbound.common.AbstractHibernateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ChildService extends AbstractHibernateService<Child> implements IChildService {

    @Autowired
    private IChildDao dao;

    public ChildService() {
        super();
    }

    // API

    @Override
    protected IOperations<Child> getDao() {
        return dao;
    }

}
