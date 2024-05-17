package com.baeldung.beanfactorypostprocessor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.boot.context.properties.bind.BindResult;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import com.baeldung.envvariables.BaeldungProperties;

@Component
public class PropertiesWithBeanFactoryPostProcessor implements BeanFactoryPostProcessor, EnvironmentAware {

    private static final Logger LOGGER = LoggerFactory.getLogger(PropertiesWithBeanFactoryPostProcessor.class);

    private Environment environment;

    @Override
    public void postProcessBeanFactory(@NonNull ConfigurableListableBeanFactory beanFactory) throws BeansException {
        String serviceName = environment.getProperty("custom-properties.name", String.class);
        LOGGER.debug("Service name, using environment::getProperty: " + serviceName);

        BindResult<BaeldungProperties> result = Binder.get(environment)
            .bind("baeldung", BaeldungProperties.class);
        BaeldungProperties properties = result.get();
        LOGGER.debug("Presentation, using binder to access BaeldungProperties: " + properties.getPresentation());

        BeanDefinitionRegistry registry = (BeanDefinitionRegistry) beanFactory;
        registry.registerBeanDefinition("presentationValueFromComponentAnnotation", BeanDefinitionBuilder.genericBeanDefinition(String.class)
            .addConstructorArgValue(serviceName + " " + properties.getPresentation())
            .getBeanDefinition());
    }

    @Override
    public void setEnvironment(@NonNull Environment environment) {
        this.environment = environment;
    }
}
