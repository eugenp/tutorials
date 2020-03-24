package com.baeldung.jupiter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.DependencyDescriptor;
import org.springframework.context.ApplicationContext;
import org.springframework.core.MethodParameter;
import org.springframework.core.annotation.AnnotatedElementUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Optional;

import static org.springframework.core.annotation.AnnotatedElementUtils.hasAnnotation;

abstract class ParameterAutowireUtils {

    private ParameterAutowireUtils() {
    }

    public static boolean isAutowirable(Parameter parameter) {
        return ApplicationContext.class.isAssignableFrom(parameter.getType()) || hasAnnotation(parameter, Autowired.class) || hasAnnotation(parameter, Qualifier.class) || hasAnnotation(parameter, Value.class);
    }

    public static Object resolveDependency(Parameter parameter, Class<?> containingClass, ApplicationContext applicationContext) {

        boolean required = findMergedAnnotation(parameter, Autowired.class).map(Autowired::required)
            .orElse(true);
        MethodParameter methodParameter = (parameter.getDeclaringExecutable() instanceof Method ? MethodParameterFactory.createSynthesizingMethodParameter(parameter) : MethodParameterFactory.createMethodParameter(parameter));
        DependencyDescriptor descriptor = new DependencyDescriptor(methodParameter, required);
        descriptor.setContainingClass(containingClass);

        return applicationContext.getAutowireCapableBeanFactory()
            .resolveDependency(descriptor, null);
    }

    private static <A extends Annotation> Optional<A> findMergedAnnotation(AnnotatedElement element, Class<A> annotationType) {

        return Optional.ofNullable(AnnotatedElementUtils.findMergedAnnotation(element, annotationType));
    }
}
