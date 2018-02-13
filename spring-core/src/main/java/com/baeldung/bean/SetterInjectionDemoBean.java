package com.baeldung.bean;

import com.baeldung.dependency.DemoDependency;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SetterInjectionDemoBean {

    private DemoDependency demoDependency;

    public SetterInjectionDemoBean(DemoDependency demoDependency) {
        this.demoDependency = demoDependency;
    }

    public DemoDependency getDemoDependency() {
        return demoDependency;
    }

    @Autowired
    public void setDemoDependency(DemoDependency demoDependency) {
        this.demoDependency = demoDependency;
    }
}
