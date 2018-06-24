package com.baeldung.scope.singletone;

import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;

import com.baeldung.scope.prototype.PrototypeBeanWithParam;

public class SingletonFunctionBean {
    
    @Autowired
    private Function<String, PrototypeBeanWithParam> beanFactory;
    
    public PrototypeBeanWithParam getPrototypeInstance(String name) {
        PrototypeBeanWithParam bean = beanFactory.apply(name);
        return bean;
    }

}
