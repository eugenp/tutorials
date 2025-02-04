package com.baeldung.persistence.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baeldung.persistence.dao.IParentDao;
import com.baeldung.persistence.dao.common.IOperations;
import com.baeldung.persistence.model.Parent;
import com.baeldung.persistence.service.IParentService;
import com.baeldung.persistence.service.common.AbstractHibernateService;

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
