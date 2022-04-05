package com.baeldung.component.autoproxying;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;

public class NotEligibleForAutoProxyRandomIntProcessor implements BeanPostProcessor {
    private final RandomIntGenerator randomIntGenerator;

    public NotEligibleForAutoProxyRandomIntProcessor(RandomIntGenerator randomIntGenerator) {
        this.randomIntGenerator = randomIntGenerator;
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        Field[] fields = bean.getClass().getDeclaredFields();
        for (Field field : fields) {
            RandomInt injectRandomInt = field.getAnnotation(RandomInt.class);
            if (injectRandomInt != null) {
                int min = injectRandomInt.min();
                int max = injectRandomInt.max();
                int randomValue = randomIntGenerator.generate(min, max);
                field.setAccessible(true);
                ReflectionUtils.setField(field, bean, randomValue);
            }
        }
        return bean;
    }
}
