package com.baeldung.dependencyinjectiontypeseval;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class DependencyInjectionTypesEvalTest {

    private ApplicationContext context = new AnnotationConfigApplicationContext(Config.class);

    @Test
    public void givenAutowiredAnnotation_WhenSetOnConstructor_ThenDependencyValid() {
        ConstructorInjection constructorInjection = getBeanFromJavaConfig(ConstructorInjection.class);

        String expectedText = "ConstructorInjection test";
        String echoedText = constructorInjection.echo(expectedText);
        System.out.println(echoedText);

        assertEquals(expectedText, echoedText);
    }

    @Test
    public void givenAutowiredAnnotation_WhenSetOnSetter_ThenDependencyValid() {
        SetterInjection setterInjection = getBeanFromJavaConfig(SetterInjection.class);

        String expectedText = "SetterInjection test";
        String echoedText = setterInjection.echo(expectedText);
        System.out.println(echoedText);

        assertEquals(expectedText, echoedText);
    }

    @Test
    public void givenAutowiredAnnotation_WhenSetOnField_ThenDependencyValid() {
        FieldInjection fieldInjection = getBeanFromJavaConfig(FieldInjection.class);

        String expectedText = "FieldInjection test";
        String echoedText = fieldInjection.echo(expectedText);
        System.out.println(echoedText);

        assertEquals(expectedText, echoedText);
    }

    private <T> T getBeanFromJavaConfig(Class<T> beanClass) {
        return context.getBean(beanClass);
    }

}
