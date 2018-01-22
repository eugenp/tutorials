package com.baeldung.dependencyinjectiontypes;

import org.springframework.beans.factory.annotation.Autowired;


public class ConstructorInjection {

    private final MyInjectedBean myInjectedBean;

    @Autowired
    public ConstructorInjection(MyInjectedBean myInjectedBean) {
        this.myInjectedBean = myInjectedBean;
    }

    public MyInjectedBean getMyInjectedBean() {
        return myInjectedBean;
    }
}
