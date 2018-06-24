package com.baeldung.scope;

import java.util.function.Function;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;

import com.baeldung.scope.prototype.PrototypeBeanWithParam;

@Configuration
public class PrototypeFactoryBeanConfig {
    
    @Bean
    @Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE, proxyMode = ScopedProxyMode.TARGET_CLASS)
    public PrototypeBeanWithParam prototypeBeanWithParam(String name) {
        return new PrototypeBeanWithParam(name);
    }
    
    @Bean
    public Function<String, PrototypeBeanWithParam> prototypeBeanFactory() {
            return runtimeArg -> prototypeBeanWithParam(runtimeArg);
    }

}
