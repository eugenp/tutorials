package org.baeldung.spring.persistence.service.impl;

import org.baeldung.spring.persistence.dao.IParentDao;
import org.baeldung.spring.persistence.dao.common.IOperations;
import org.baeldung.spring.persistence.model.Parent;
import org.baeldung.spring.persistence.service.IParentService;
import org.baeldung.spring.persistence.service.common.AbstractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ParentService extends AbstractService<Parent> implements IParentService {

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
