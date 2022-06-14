package com.baeldung.countingbeans.olderspring.factorybeans;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class AnnotatedBeansComponent {

    @Autowired
    GenericApplicationContext applicationContext;

    public List<String> getBeansWithAnnotation(Class<?> annotationClass) {
        return BeanUtils.getBeansWithAnnotation(applicationContext, annotationClass);
    }

}