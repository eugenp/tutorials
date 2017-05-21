package org.baeldung.bean.injection;

import org.springframework.beans.factory.annotation.Autowired;

public class SpringBeanInjectionWithConstructor {
    private MyInterfaceClass dataProviderInstance;


    public SpringBeanInjectionWithConstructor(@Autowired MyInterfaceClass dataProviderInstance) {
        this.dataProviderInstance = dataProviderInstance;
    }
}