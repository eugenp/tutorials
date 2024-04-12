package com.baeldung.config.scope;

import java.util.function.Function;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import com.baeldung.scope.prototype.PrototypeBean;
import com.baeldung.scope.singleton.SingletonFunctionBean;

@Configuration
public class AppConfigFunctionBean {

    @Bean
    public Function<String, PrototypeBean> beanFactory() {
        return name -> prototypeBeanWithParam(name);
    } 

    @Bean
    @Scope(value = "prototype")
    public PrototypeBean prototypeBeanWithParam(String name) {
       return new PrototypeBean(name);
    }
    
    @Bean
    public SingletonFunctionBean singletonFunctionBean() {
        return new SingletonFunctionBean();
    }
    
}
