package com.baeldung.ioccontainer.bean;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

public class CustomBeanPostProcessor implements BeanPostProcessor {

    static final Logger LOGGER = LogManager.getLogger(CustomBeanPostProcessor.class.getName());

    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        LOGGER.info("BeanPostProcessor is Registered Before Initialization");
        return bean;
    }

}
