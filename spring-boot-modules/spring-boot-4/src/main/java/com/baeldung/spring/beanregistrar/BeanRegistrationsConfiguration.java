package com.baeldung.spring.beanregistrar;

import org.springframework.beans.factory.BeanRegistrar;
import org.springframework.beans.factory.BeanRegistry;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.env.Environment;

@ConditionalOnProperty(name = "application.registration-v7", havingValue = "true", matchIfMissing = true)
@Configuration
@Import(BeanRegistrationsConfiguration.MyBeanRegistrar.class)
public class BeanRegistrationsConfiguration {

    static class MyBeanRegistrar implements BeanRegistrar {

        @Override
        public void register(BeanRegistry registry, Environment env) {
            registry.registerBean("myService", MyService.class);
            registry.registerBean("service2", MyService.class, spec -> spec.prototype()
                .lazyInit()
                .primary());
        }
    }

}
