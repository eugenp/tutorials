package com.baeldung.dependencyInjections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ConstructorBasedInjection {

    private InjectedBean injectedBean;

    @Autowired
    public ConstructorBasedInjection(InjectedBean injectedBean) {
        System.out.println("ConstructorBasedInjection: Constructor called.");
        this.injectedBean = injectedBean;
        System.out.println("ConstructorBasedInjection: " + this.injectedBean);
    }
}
