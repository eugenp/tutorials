package com.baeldung.scope.singleton;

import com.baeldung.scope.prototype.PrototypeBean;
import org.springframework.beans.factory.annotation.Autowired;

import javax.inject.Provider;

public class SingletonProviderBean {

    @Autowired
    private Provider<PrototypeBean> myPrototypeBeanProvider;

    public PrototypeBean getPrototypeInstance() {
        return myPrototypeBeanProvider.get();
    }
}
