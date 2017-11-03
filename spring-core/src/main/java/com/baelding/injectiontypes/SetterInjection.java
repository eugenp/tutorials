package com.baelding.injectiontypes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SetterInjection {
    
    private UsefulBean usefulBean;
    
    @Autowired
    public void setUsefulBean(UsefulBean usefulBean) {
        this.usefulBean = usefulBean;
    }
    
    public String getParameters() {
        return "Using setter injection, we got these parameters " + usefulBean.getParameters();
    }
    
}
