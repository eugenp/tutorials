package com.baeldung.dependency.injection.field.cyclic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BeanA {
    private CyclicFieldInjectionBean parent;

    @Autowired
    public void setParent(CyclicFieldInjectionBean parent) {
        this.parent = parent;
    }

    public CyclicFieldInjectionBean getParent() {
        return parent;
    }
}
