package com.baeldung.beanfactorypostprocessor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.boot.context.properties.bind.BindResult;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

@Configuration
public class PropertiesWithBeanFactoryPostProcessorConfig {

    private static final Logger LOGGER = LoggerFactory.getLogger(PropertiesWithBeanFactoryPostProcessorConfig.class);

    @Bean
    public static BeanFactoryPostProcessor beanFactoryPostProcessor(Environment environment) {
        return beanFactory -> {
            String articleName = environment.getProperty("article.name", String.class);
            LOGGER.debug("Article name, using environment::getProperty: " + articleName);

            BindResult<ApplicationProperties> result = Binder.get(environment)
                .bind("application", ApplicationProperties.class);
            ApplicationProperties properties = result.get();
            LOGGER.debug("Application name, using binder to access ApplicationProperties: " + properties.getName());

            BeanDefinitionRegistry registry = (BeanDefinitionRegistry) beanFactory;
            registry.registerBeanDefinition("tutorialTitleFromBeanAnnotation", BeanDefinitionBuilder.genericBeanDefinition(String.class)
                .addConstructorArgValue(properties.getName() + ": " + articleName)
                .getBeanDefinition());
        };
    }
}
