package com.baelding.injectiontypes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FieldInjection {

    @Autowired
    private UsefulBean usefulBean;
    
    public String getParameters() {
        return "Using field injection, we got these parameters " + usefulBean.getParameters();
    }

}
