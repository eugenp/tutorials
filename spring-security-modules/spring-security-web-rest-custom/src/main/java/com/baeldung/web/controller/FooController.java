package com.baeldung.web.controller;

import com.baeldung.service.IFooService;
import com.baeldung.web.dto.Foo;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/foos")
public class FooController implements InitializingBean {

    @Value("${foo1}")
    private String foo1;

    @Autowired
    private Environment env;

    @Autowired
    private IFooService service;

    public FooController() {
        super();
    }

    // API

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Foo findOne(@PathVariable("id") final Long id) {
        return service.findOne(id);
    }

    @Override
    public final void afterPropertiesSet() {
        System.out.println("In Child Context, property via @Value = " + foo1);
        System.out.println("In Child Context, property via env = " + env.getProperty("foo2"));
    }

}
