package com.baeldung.bean;

import com.baeldung.dependency.DemoDependency;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ConstructorInjectionDemoBean {

    private DemoDependency demoDependency;

    @Autowired
    public ConstructorInjectionDemoBean(DemoDependency demoDependency) {
        this.demoDependency = demoDependency;
    }

    public DemoDependency getDemoDependency() {
        return demoDependency;
    }
}
