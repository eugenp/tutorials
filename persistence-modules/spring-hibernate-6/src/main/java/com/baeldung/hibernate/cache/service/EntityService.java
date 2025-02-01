package com.baeldung.hibernate.cache.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baeldung.hibernate.cache.dao.IBarRepo;
import com.baeldung.hibernate.cache.dao.IFooRepo;
import com.baeldung.hibernate.cache.dao.IRooRepo;
import com.baeldung.hibernate.cache.model.Bar;
import com.baeldung.hibernate.cache.model.Foo;
import com.baeldung.hibernate.cache.model.Roo;

@Service
@Transactional
public class EntityService {

    private final IFooRepo foos;

    private final IBarRepo bars;

    private final IRooRepo roos;

    @Autowired
    public EntityService(IFooRepo foos, IBarRepo bars, IRooRepo roos) {
        this.foos = foos;
        this.bars = bars;
        this.roos = roos;
    }

    public void create(final Foo entity) {
        foos.save(entity);
    }

    public void create(final Roo entity) {
        roos.save(entity);
    }

    public List<Foo> findAll() {
        return foos.findAll();
    }

    public Bar findOneBar(long id) {
        return bars.findById(id)
            .orElseThrow();
    }

    public Foo findOneFoo(final long id) {
        return foos.findById(id)
            .orElseThrow();
    }

    public Roo findOneRoo(final long id) {
        return roos.findById(id)
            .orElseThrow();
    }
}