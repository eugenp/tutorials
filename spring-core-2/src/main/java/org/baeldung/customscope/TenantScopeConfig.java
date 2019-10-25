package org.baeldung.customscope;

import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TenantScopeConfig {

    @Bean
    public static BeanFactoryPostProcessor beanFactoryPostProcessor() {
        return new TenantBeanFactoryPostProcessor();
    }
}
