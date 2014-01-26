package org.baeldung.service;

import org.baeldung.web.dto.Foo;
import org.springframework.stereotype.Service;

@Service
public class FooService implements IFooService {

    public FooService() {
        super();
    }

    // API

    @Override
    public Foo findOne(final Long id) {
        return new Foo();
    }

}
