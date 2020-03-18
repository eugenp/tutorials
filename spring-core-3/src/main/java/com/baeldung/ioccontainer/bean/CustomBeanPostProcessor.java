package com.baeldung.ioccontainer.bean;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

public class CustomBeanPostProcessor implements BeanPostProcessor {
    public static boolean isBeanInstantiated = false;
    
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        isBeanInstantiated = true;
        return bean;
    }
}
