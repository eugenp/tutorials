package com.baeldung.service;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.baeldung.web.dto.Foo;

@Service
public class FooService implements IFooService, InitializingBean {

    @Value("${foo1}")
    private String foo1;

    private final Environment env;

    public FooService(Environment env) {
        super();
        this.env = env;
    }

    @Override
    public Foo findOne(final Long id) {
        return new Foo();
    }

    @Override
    public final void afterPropertiesSet() {
        System.out.println("In Parent Context, property via @Value = " + foo1);
        System.out.println("In Parent Context, property via env = " + env.getProperty("foo2"));
    }

}
