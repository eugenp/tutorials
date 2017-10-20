package com.baeldung.bizzkit;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(value={"com.baeldung.bizzkit"})
public class InjectionConfigurator {

    @Bean
    public BusinessService getBusinessService() {
        return new BusinessService();
    }

}
