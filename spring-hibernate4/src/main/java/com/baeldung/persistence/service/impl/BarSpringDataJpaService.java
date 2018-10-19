package com.baeldung.persistence.service.impl;

import java.io.Serializable;

import com.baeldung.persistence.service.common.AbstractSpringDataJpaService;
import com.baeldung.persistence.dao.IBarCrudRepository;
import com.baeldung.persistence.model.Bar;
import com.baeldung.persistence.service.IBarService;
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
