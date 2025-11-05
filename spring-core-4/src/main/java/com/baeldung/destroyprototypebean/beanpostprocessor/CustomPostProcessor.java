package com.baeldung.destroyprototypebean.beanpostprocessor;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.DestructionAwareBeanPostProcessor;
import org.springframework.stereotype.Component;

@Component
public class CustomPostProcessor implements DestructionAwareBeanPostProcessor {

    @Override
    public void postProcessBeforeDestruction(Object bean, String beanName) throws BeansException {
        if (bean instanceof PostProcessorBeanExample) {
            ((PostProcessorBeanExample) bean).destroy();
        }
    }

}
