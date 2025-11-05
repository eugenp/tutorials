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

@Component
public class PropertiesWithBeanFactoryPostProcessor implements BeanFactoryPostProcessor, EnvironmentAware {

    private static final Logger LOGGER = LoggerFactory.getLogger(PropertiesWithBeanFactoryPostProcessor.class);

    private Environment environment;

    @Override
    public void postProcessBeanFactory(@NonNull ConfigurableListableBeanFactory beanFactory) throws BeansException {
        String articleName = environment.getProperty("article.name", String.class);
        LOGGER.debug("Article name, using environment::getProperty: " + articleName);

        BindResult<ApplicationProperties> result = Binder.get(environment)
            .bind("application", ApplicationProperties.class);
        ApplicationProperties properties = result.get();
        LOGGER.debug("Application name, using binder to access ApplicationProperties: " + properties.getName());

        BeanDefinitionRegistry registry = (BeanDefinitionRegistry) beanFactory;
        registry.registerBeanDefinition("tutorialTitleFromComponentAnnotation", BeanDefinitionBuilder.genericBeanDefinition(String.class)
            .addConstructorArgValue(properties.getName() + " - " + articleName)
            .getBeanDefinition());
    }

    @Override
    public void setEnvironment(@NonNull Environment environment) {
        this.environment = environment;
    }
}
