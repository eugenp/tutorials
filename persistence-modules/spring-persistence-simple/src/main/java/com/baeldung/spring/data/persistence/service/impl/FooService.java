package com.baeldung.spring.data.persistence.service.impl;


import com.baeldung.spring.data.persistence.model.Foo;
import com.baeldung.spring.data.persistence.repository.IFooDao;
import com.baeldung.spring.data.persistence.service.IFooService;
import com.baeldung.spring.data.persistence.service.common.AbstractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

}
