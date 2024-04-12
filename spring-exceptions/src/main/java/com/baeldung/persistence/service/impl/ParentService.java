package com.baeldung.persistence.service.impl;

import com.baeldung.persistence.common.AbstractService;
import com.baeldung.persistence.common.IOperations;
import com.baeldung.persistence.model.Parent;
import com.baeldung.persistence.service.IParentService;
import com.baeldung.persistence.dao.IParentDao;
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
