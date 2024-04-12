package com.baeldung.ioccontainer.bean;

import org.springframework.beans.factory.config.BeanPostProcessor;

public class CustomBeanPostProcessor implements BeanPostProcessor {
    private static boolean isBeanPostProcessorRegistered = false;
    
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName){
        setBeanPostProcessorRegistered(true);
        return bean;
    }

    public static boolean isBeanPostProcessorRegistered() {
        return isBeanPostProcessorRegistered;
    }

    public static void setBeanPostProcessorRegistered(boolean isBeanPostProcessorRegistered) {
        CustomBeanPostProcessor.isBeanPostProcessorRegistered = isBeanPostProcessorRegistered;
    }
}
