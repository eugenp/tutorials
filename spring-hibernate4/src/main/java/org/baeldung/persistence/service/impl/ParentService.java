package org.baeldung.persistence.service.impl;

import org.baeldung.persistence.dao.IParentDao;
import org.baeldung.persistence.dao.common.IOperations;
import org.baeldung.persistence.model.Parent;
import org.baeldung.persistence.service.IParentService;
import org.baeldung.persistence.service.common.AbstractHibernateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ParentService extends AbstractHibernateService<Parent>implements IParentService {

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
