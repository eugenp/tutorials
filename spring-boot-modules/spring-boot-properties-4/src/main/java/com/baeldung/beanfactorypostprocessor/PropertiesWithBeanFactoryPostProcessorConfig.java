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

import com.baeldung.envvariables.BaeldungProperties;

@Configuration
public class PropertiesWithBeanFactoryPostProcessorConfig {

    private static final Logger LOGGER = LoggerFactory.getLogger(PropertiesWithBeanFactoryPostProcessorConfig.class);

    @Bean
    public static BeanFactoryPostProcessor beanFactoryPostProcessor(Environment environment) {
        return beanFactory -> {
            String serviceName = environment.getProperty("custom-properties.name", String.class);
            LOGGER.debug("Service name, using environment::getProperty: " + serviceName);

            BindResult<BaeldungProperties> result = Binder.get(environment)
                .bind("baeldung", BaeldungProperties.class);
            BaeldungProperties properties = result.get();
            LOGGER.debug("Presentation, using binder to access BaeldungProperties: " + properties.getPresentation());

            BeanDefinitionRegistry registry = (BeanDefinitionRegistry) beanFactory;
            registry.registerBeanDefinition("presentationValueFromBeanAnnotation", BeanDefinitionBuilder.genericBeanDefinition(String.class)
                .addConstructorArgValue(serviceName + " " + properties.getPresentation())
                .getBeanDefinition());
        };
    }
}
