package com.baeldung.quarkus.shared.interceptors;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Optional;

import lombok.experimental.UtilityClass;

@UtilityClass
class AnnotationUtils {

    <A extends Annotation> Optional<A> findAnnotation(Method method, Class<A> annotationClass) {
        Optional<A> result = Optional.ofNullable(method.getAnnotation(annotationClass));
        // since Java 9, we could simply use Optional#or(...)
        if (!result.isPresent()) {
            result = findAnnotation(method.getDeclaringClass(), annotationClass);
        }
        return result;
    }

    <A extends Annotation> Optional<A> findAnnotation(Class<?> clazz, Class<A> annotationClass) {
        return Optional.ofNullable(clazz.getAnnotation(annotationClass));
    }

}