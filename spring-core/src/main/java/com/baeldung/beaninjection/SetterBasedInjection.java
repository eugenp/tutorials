package com.baeldung.beaninjection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SetterBasedInjection {

    private DependencyBean dependencyBean;

    @Autowired
    public void setDependencyBean(DependencyBean dependencyBean) {
        this.dependencyBean = dependencyBean;
    }

    public DependencyBean getDependencyBean() {
        return dependencyBean;
    }

}