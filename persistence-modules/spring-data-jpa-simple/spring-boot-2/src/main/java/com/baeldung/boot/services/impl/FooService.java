package com.baeldung.boot.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baeldung.boot.daos.IFooDao;
import com.baeldung.boot.domain.Foo;
import com.baeldung.boot.services.IFooService;
import com.google.common.collect.Lists;

@Service
@Transactional
public class FooService extends AbstractService<Foo> implements IFooService {

    @Autowired
    private IFooDao dao;

    public FooService() {
        super();
    }

    // API

    @Override
    protected PagingAndSortingRepository<Foo, Long> getDao() {
        return dao;
    }

    // custom methods

    @Override
    public Foo retrieveByName(final String name) {
        return dao.retrieveByName(name);
    }

    // overridden to be secured

    @Override
    @Transactional(readOnly = true)
    public List<Foo> findAll() {
        return Lists.newArrayList(getCrud().findAll());
    }

    @Override
    public Page<Foo> findPaginated(Pageable pageable) {
        return dao.findAll(pageable);
    }

    @Override
    protected CrudRepository<Foo, Long> getCrud() {
        return null;
    }

}
