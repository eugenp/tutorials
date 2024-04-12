package com.baeldung.ioccontainer.bean;

import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;

public class CustomBeanFactoryPostProcessor implements BeanFactoryPostProcessor {
    private static boolean isBeanFactoryPostProcessorRegistered = false;
    
    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory){
        setBeanFactoryPostProcessorRegistered(true);
    }

    public static boolean isBeanFactoryPostProcessorRegistered() {
        return isBeanFactoryPostProcessorRegistered;
    }

    public static void setBeanFactoryPostProcessorRegistered(boolean isBeanFactoryPostProcessorRegistered) {
        CustomBeanFactoryPostProcessor.isBeanFactoryPostProcessorRegistered = isBeanFactoryPostProcessorRegistered;
    }
}
