package com.baeldung.bootique.module;

import com.baeldung.bootique.service.HelloService;
import com.baeldung.bootique.service.impl.HelloServiceImpl;
import com.google.inject.Binder;
import com.google.inject.Module;

public class ModuleBinder implements Module {

    @Override
    public void configure(Binder binder) {
        binder.bind(HelloService.class).to(HelloServiceImpl.class);
    }

}
