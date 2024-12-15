package com.baeldung.customannotation;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;
import org.springframework.util.ReflectionUtils.FieldCallback;

@Component
public class DataAccessAnnotationProcessor implements BeanPostProcessor {

    private ConfigurableListableBeanFactory configurableListableBeanFactory;

    @Autowired
    public DataAccessAnnotationProcessor(ConfigurableListableBeanFactory bf) {
        configurableListableBeanFactory = bf;
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        scanDataAccessAnnotation(bean, beanName);
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    protected void scanDataAccessAnnotation(Object bean, String beanName) {
        Class<?> managedBeanClass = bean.getClass();
        FieldCallback fcb = new DataAccessFieldCallback(configurableListableBeanFactory, bean);
        ReflectionUtils.doWithFields(managedBeanClass, fcb);
    }
}
