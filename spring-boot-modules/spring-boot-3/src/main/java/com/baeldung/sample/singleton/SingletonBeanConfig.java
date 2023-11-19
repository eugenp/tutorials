package com.baeldung.sample.singleton;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
public class SingletonBeanConfig {

    @Bean
    @Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
    public SingletonBean singletonBean() {
        return new SingletonBean();
    }

    @Bean
    @Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
    public SingletonBean anotherSingletonBean() {
        return new SingletonBean();
    }

    static class SingletonBean {
        public String getValue() {
            return "test";
        }
    }

}
