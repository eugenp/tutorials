package com.baeldung.persistence.service.impl;

import com.baeldung.persistence.common.AbstractService;
import com.baeldung.persistence.common.IOperations;
import com.baeldung.persistence.service.IChildService;
import com.baeldung.persistence.dao.IChildDao;
import com.baeldung.persistence.model.Child;
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
