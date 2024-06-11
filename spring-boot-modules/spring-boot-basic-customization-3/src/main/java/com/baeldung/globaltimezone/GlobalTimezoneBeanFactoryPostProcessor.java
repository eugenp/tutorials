package com.baeldung.globaltimezone;

import java.util.TimeZone;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.stereotype.Component;

@Component
public class GlobalTimezoneBeanFactoryPostProcessor implements BeanFactoryPostProcessor {

    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalTimezoneBeanFactoryPostProcessor.class);

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        LOGGER.info("Default timezone, before bean factory post processor, is set to: " + TimeZone.getDefault()
            .getDisplayName());

        TimeZone.setDefault(TimeZone.getTimeZone("GMT+08:00"));
    }
}
