package com.baeldung.destroyprototypebean.destroymethod;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import com.baeldung.destroyprototypebean.destorymethod.CustomMethodBeanExample;

@Configuration
@ComponentScan(basePackages = "com.baeldung.destroyprototypebean.destroymethod")
public class DestroyMethodConfig {

    @Bean(destroyMethod = "destroy")
    @Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public CustomMethodBeanExample customMethodBeanExample() {
        return new CustomMethodBeanExample();
    }

}
