package org.baeldung.persistence.service.impl;

import org.baeldung.persistence.dao.IChildDao;
import org.baeldung.persistence.dao.common.IOperations;
import org.baeldung.persistence.model.Child;
import org.baeldung.persistence.service.IChildService;
import org.baeldung.persistence.service.common.AbstractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ChildService extends AbstractService<Child>implements IChildService {

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
