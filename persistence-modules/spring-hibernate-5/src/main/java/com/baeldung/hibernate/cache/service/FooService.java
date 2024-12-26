package com.baeldung.hibernate.cache.service;

import com.baeldung.hibernate.cache.dao.IFooDao;
import com.baeldung.hibernate.cache.model.Foo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class FooService {

    @Autowired
    private IFooDao dao;

    public FooService() {
        super();
    }

    // API

    public void create(final Foo entity) {
        dao.create(entity);
    }

    public Foo findOne(final long id) {
        return dao.findOne(id);
    }

    public List<Foo> findAll() {
        return dao.findAll();
    }

    public long countAllRowsUsingHibernateCriteria() {
        return dao.countAllRowsUsingHibernateCriteria(); // Cast to FooDao to access AbstractJpaDAO method
    }

    public long getFooCountByBarNameUsingHibernateCriteria(String barName) {
        return dao.getFooCountByBarNameUsingHibernateCriteria(barName);
    }

    public long getFooCountByBarNameAndFooNameUsingHibernateCriteria(String barName, String fooName) {
        return dao.getFooCountByBarNameAndFooNameUsingHibernateCriteria(barName, fooName);
    }
}
