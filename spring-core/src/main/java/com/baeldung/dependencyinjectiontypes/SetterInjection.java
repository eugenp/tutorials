package com.baeldung.dependencyinjectiontypes;

import org.springframework.beans.factory.annotation.Autowired;


public class SetterInjection
{

    private MyInjectedBean myInjectedBean;

    @Autowired
    public void setMyInjectedBean(MyInjectedBean myInjectedBean) {
        this.myInjectedBean = myInjectedBean;
    }

    public MyInjectedBean getMyInjectedBean() {
        return myInjectedBean;
    }
}
