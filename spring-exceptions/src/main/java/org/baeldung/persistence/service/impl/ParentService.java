package org.baeldung.persistence.service.impl;

import org.baeldung.persistence.common.AbstractService;
import org.baeldung.persistence.common.IOperations;
import org.baeldung.persistence.dao.IParentDao;
import org.baeldung.persistence.model.Parent;
import org.baeldung.persistence.service.IParentService;
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
