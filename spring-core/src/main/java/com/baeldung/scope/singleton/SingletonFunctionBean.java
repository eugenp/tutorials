package com.baeldung.scope.singleton;

import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;

import com.baeldung.scope.prototype.PrototypeBean;

public class SingletonFunctionBean {
    
    @Autowired
    private Function<String, PrototypeBean> beanFactory;
    
    public PrototypeBean getPrototypeInstance(String name) {
        PrototypeBean bean = beanFactory.apply(name);
        return bean;
    }

}
