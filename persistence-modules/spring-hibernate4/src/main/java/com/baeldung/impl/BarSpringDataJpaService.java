package com.baeldung.port.inbound.impl;

import java.io.Serializable;

import com.baeldung.port.inbound.common.AbstractSpringDataJpaService;
import com.baeldung.port.outbound.IBarCrudRepository;
import com.baeldung.port.model.Bar;
import com.baeldung.port.inbound.IBarService;
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
