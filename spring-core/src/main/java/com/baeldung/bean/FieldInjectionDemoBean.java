package com.baeldung.bean;

import com.baeldung.dependency.DemoDependency;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FieldInjectionDemoBean {

    @Autowired
    private DemoDependency demoDependency;

    public FieldInjectionDemoBean(DemoDependency demoDependency) {
        this.demoDependency = demoDependency;
    }

    public DemoDependency getDemoDependency() {
        return demoDependency;
    }
}
