package org.baeldung.bean.injection;

import org.springframework.beans.factory.annotation.Autowired;

public class SpringBeanInjectionWithSetter {
    private MyInterfaceClass dataProviderInstance;

    @Autowired
    public void setDataProviderInstance(MyInterfaceClass dataProviderInstance) {
        this.dataProviderInstance = dataProviderInstance;
    }
}