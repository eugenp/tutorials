package com.baeldung.dependency.injection.setter.cyclic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DependentBean {
    private CyclicSetterInjectionBean parent;

    @Autowired
    public void setParent(CyclicSetterInjectionBean parent) {
        this.parent = parent;
    }

    public CyclicSetterInjectionBean getParent() {
        return parent;
    }
}
