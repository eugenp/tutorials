package org.baeldung.spring.persistence.service.impl;

import org.baeldung.spring.persistence.dao.IChildDao;
import org.baeldung.spring.persistence.dao.common.IOperations;
import org.baeldung.spring.persistence.model.Child;
import org.baeldung.spring.persistence.service.IChildService;
import org.baeldung.spring.persistence.service.common.AbstractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ChildService extends AbstractService<Child> implements IChildService {

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
