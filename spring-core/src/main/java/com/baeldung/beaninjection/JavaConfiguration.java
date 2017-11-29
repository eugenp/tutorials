package com.baeldung.beaninjection;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JavaConfiguration {

        @Bean
        public ServiceA serviceA() {
                return new ServiceA();
        }

        @Bean
        public ServiceB serviceB() {
                return new ServiceB();
        }

        @Bean
        public ManagedBean managedBean(ServiceA serviceA, ServiceB serviceB) {
                ManagedBean toReturn = new ManagedBean(serviceA);
                toReturn.setServiceB(serviceB);
                return toReturn;
        }

}
