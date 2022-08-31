package com.baeldung.countingbeans.olderspring.factorybeans;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

public class BeanUtils {

    // NB : this method lists only beans created via factory methods
    public static List<String> getBeansWithAnnotation(GenericApplicationContext applicationContext, Class<?> annotationClass) {
        List<String> result = new ArrayList<String>();
        ConfigurableListableBeanFactory factory = applicationContext.getBeanFactory();
        for(String name : factory.getBeanDefinitionNames()) {
            BeanDefinition bd = factory.getBeanDefinition(name);
            if(bd.getSource() instanceof AnnotatedTypeMetadata) {
                AnnotatedTypeMetadata metadata = (AnnotatedTypeMetadata) bd.getSource();
                if (metadata.getAnnotationAttributes(annotationClass.getName()) != null) {
                    result.add(name);
                }
            }
        }
        return result;
    }

    // NB : list beans created via factory methods using streams (same method as before, written differently)
    public static List<String> getBeansWithAnnotation_StreamVersion(GenericApplicationContext applicationContext, Class<?> annotationClass) {
        ConfigurableListableBeanFactory factory = applicationContext.getBeanFactory();
        return Arrays.stream(factory.getBeanDefinitionNames())
            .filter(name -> isAnnotated(factory, name, annotationClass))
            .collect(Collectors.toList());
    }

    private static boolean isAnnotated(ConfigurableListableBeanFactory factory, String beanName, Class<?> clazz) {
        BeanDefinition beanDefinition = factory.getBeanDefinition(beanName);
        if(beanDefinition.getSource() instanceof AnnotatedTypeMetadata) {
            AnnotatedTypeMetadata metadata = (AnnotatedTypeMetadata) beanDefinition.getSource();
            return metadata.getAnnotationAttributes(clazz.getName()) != null;
        }
        return false;
    }

}