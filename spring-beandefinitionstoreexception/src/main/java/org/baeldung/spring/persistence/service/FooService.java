package org.baeldung.spring.persistence.service;

import org.baeldung.spring.persistence.dao.IFooDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FooService {

    @Autowired
    private IFooDao fooDao;

    public FooService() {
        super();
    }

    // API

}
