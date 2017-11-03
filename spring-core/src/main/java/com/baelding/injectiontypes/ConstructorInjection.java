package com.baelding.injectiontypes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ConstructorInjection {

    private UsefulBean usefulBean;

    @Autowired
    public ConstructorInjection(UsefulBean usefulBean) {
        this.usefulBean = usefulBean;
    }

    public String getParameters() {
        return "Using constructor injection, we got these parameters " + usefulBean.getParameters();
    }

}
