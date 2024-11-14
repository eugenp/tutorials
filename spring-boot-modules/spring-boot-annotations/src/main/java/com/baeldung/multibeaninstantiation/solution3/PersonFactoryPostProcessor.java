package com.baeldung.multibeaninstantiation.solution3;

import java.util.Map;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;

public class PersonFactoryPostProcessor implements BeanFactoryPostProcessor {

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        Map<String, Object> map = beanFactory.getBeansWithAnnotation(Qualifier.class);
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            createInstances(beanFactory, entry.getKey(), entry.getValue());
        }
    }

    private void createInstances(ConfigurableListableBeanFactory beanFactory, String beanName, Object bean) {
        Qualifier qualifier = bean.getClass()
            .getAnnotation(Qualifier.class);
        for (String name : extractNames(qualifier)) {
            Object newBean = beanFactory.getBean(beanName);
            beanFactory.registerSingleton(name.trim(), newBean);
        }
    }

    private String[] extractNames(Qualifier qualifier) {
        return qualifier.value()
            .split(",");
    }
}