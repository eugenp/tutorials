package org.baeldung.persistence.service.impl;

import java.io.Serializable;

import org.baeldung.persistence.dao.IBarCrudRepository;
import org.baeldung.persistence.model.Bar;
import org.baeldung.persistence.service.IBarService;
import org.baeldung.persistence.service.common.AbstractSpringDataJpaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;

public class BarSpringDataJpaService extends AbstractSpringDataJpaService<Bar> implements IBarService {

    @Autowired
    private IBarCrudRepository dao;

    public BarSpringDataJpaService() {
        super();
    }

    @Override
    protected CrudRepository<Bar, Serializable> getDao() {
        return dao;
    }

}
