/*
 * Copyright 2002-2016 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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
        return ApplicationContext.class.isAssignableFrom(parameter.getType())
          || hasAnnotation(parameter, Autowired.class)
          || hasAnnotation(parameter, Qualifier.class)
          || hasAnnotation(parameter, Value.class);
    }

    public static Object resolveDependency(Parameter parameter
      , Class<?> containingClass, ApplicationContext applicationContext) {

        boolean required = findMergedAnnotation(parameter, Autowired.class)
          .map(Autowired::required).orElse(true);
        MethodParameter methodParameter = (parameter.getDeclaringExecutable()
          instanceof Method
          ? MethodParameterFactory.createSynthesizingMethodParameter(parameter)
          : MethodParameterFactory.createMethodParameter(parameter));
        DependencyDescriptor descriptor = new DependencyDescriptor(methodParameter
          , required);
        descriptor.setContainingClass(containingClass);

        return applicationContext.getAutowireCapableBeanFactory()
          .resolveDependency(descriptor, null);
    }

    private static <A extends Annotation> Optional<A> findMergedAnnotation(
      AnnotatedElement element, Class<A> annotationType) {

        return Optional.ofNullable(AnnotatedElementUtils
          .findMergedAnnotation(element, annotationType));
    }
}
