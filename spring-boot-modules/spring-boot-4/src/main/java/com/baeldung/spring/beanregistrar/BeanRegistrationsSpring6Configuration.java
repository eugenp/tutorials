package com.baeldung.spring.beanregistrar;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@ConditionalOnProperty(name = "application.registration-v7", havingValue = "false")
@SuppressWarnings("NullableProblems")
@Configuration
public class BeanRegistrationsSpring6Configuration {

    @Bean
    BeanDefinitionRegistryPostProcessor beanDefinitionRegistryPostProcessor() {
        return new BeanDefinitionRegistryPostProcessor() {

            @Override
            public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
                var beanDefinition = new GenericBeanDefinition();
                beanDefinition.setBeanClass(MyService.class);
                beanDefinition.setScope(BeanDefinition.SCOPE_SINGLETON);
                registry.registerBeanDefinition("myService", beanDefinition);
            }

            @Override
            public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
                // No-op
            }
        };
    }

}
