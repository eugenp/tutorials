package com.baeldung.dependency.injection.setter.cyclic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CyclicSetterInjectionBean {
    private DependentBean dependentBean;

    public DependentBean getDependentBean() {
        return dependentBean;
    }

    @Autowired
    public void setDependentBean(DependentBean dependentBean) {
        this.dependentBean = dependentBean;
    }
}
