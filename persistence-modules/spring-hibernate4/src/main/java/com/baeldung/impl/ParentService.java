package com.baeldung.port.inbound.impl;

import com.baeldung.port.model.Parent;
import com.baeldung.port.inbound.IParentService;
import com.baeldung.port.outbound.IParentDao;
import com.baeldung.port.outbound.common.IOperations;
import com.baeldung.port.inbound.common.AbstractHibernateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ParentService extends AbstractHibernateService<Parent> implements IParentService {

    @Autowired
    private IParentDao dao;

    public ParentService() {
        super();
    }

    // API

    @Override
    protected IOperations<Parent> getDao() {
        return dao;
    }

}
