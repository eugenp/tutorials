package com.baeldung.beaninjection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ConstructorBasedInjection {

    private DependencyBean dependencyBean;

    @Autowired
    public ConstructorBasedInjection(DependencyBean dependencyBean) {
        this.dependencyBean = dependencyBean;
    }

    public DependencyBean getDependencyBean() {
        return dependencyBean;
    }

}