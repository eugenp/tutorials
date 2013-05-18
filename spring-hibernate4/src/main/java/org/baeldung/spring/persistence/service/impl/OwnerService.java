package org.baeldung.spring.persistence.service.impl;

import org.baeldung.spring.persistence.dao.IOwnerDao;
import org.baeldung.spring.persistence.dao.common.IOperations;
import org.baeldung.spring.persistence.model.Owner;
import org.baeldung.spring.persistence.service.IOwnerService;
import org.baeldung.spring.persistence.service.common.AbstractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OwnerService extends AbstractService<Owner> implements IOwnerService {

    @Autowired
    private IOwnerDao dao;

    public OwnerService() {
        super();
    }

    // API

    @Override
    protected IOperations<Owner> getDao() {
        return dao;
    }

}
