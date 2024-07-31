package com.baeldung.web.controller;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baeldung.service.IFooService;
import com.baeldung.web.dto.Foo;

@RestController
@RequestMapping(value = "/foos")
public class FooController implements InitializingBean {

    @Value("${foo1}")
    private String foo1;

    private final Environment env;
    private final IFooService service;

    public FooController(Environment env, IFooService service) {
        super();
        this.env = env;
        this.service = service;
    }

    @GetMapping(value = "/{id}")
    public Foo findOne(@PathVariable(name = "id") final Long id) {
        return service.findOne(id);
    }

    @Override
    public final void afterPropertiesSet() {
        System.out.println("In Child Context, property via @Value = " + foo1);
        System.out.println("In Child Context, property via env = " + env.getProperty("foo2"));
    }

}
