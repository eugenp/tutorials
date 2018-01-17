package com.baeldung;

import org.springframework.beans.factory.annotation.Autowired;


public class MyBean {

    private MyInjectedBean myInjectedBean;

    // Constructor injection
    @Autowired
    public MyBean(MyInjectedBean myInjectedBean) {
        this.myInjectedBean = myInjectedBean;
    }

    // Setter injection
    @Autowired
    public void setMyInjectedBean(MyInjectedBean myInjectedBean) {
        this.myInjectedBean = myInjectedBean;
    }

}
