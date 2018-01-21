package com.baeldung.dependencyInjections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SetterBasedInjection {
    private InjectedBean setterInjectedBean;

    public SetterBasedInjection() {
        System.out.println("SetterBasedInjection: Constructor called.");
    }

    @Autowired
    public void setSetterInjectedBean(InjectedBean setterInjectedBean) {
        System.out.println("Setter Injection invoked.");
        this.setterInjectedBean = setterInjectedBean;
        System.out.println("SetterBasedInjection: " + this.setterInjectedBean);
    }
}
