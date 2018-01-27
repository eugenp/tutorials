package com.baeldung.firstarticle.dependencyinjectiontypes;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import static org.junit.Assert.assertNotNull;

public class DependencyInjectionTest {

    @Test
    public void givenAutowiredAnnotation_WhenSetOnConstructor_ThenDependencyIsNotNull() {

        ApplicationContext context = new AnnotationConfigApplicationContext("com.baeldung.firstarticle.dependencyinjectiontypes");
        SportCar sportCar = (SportCar) context.getBean("sportCar");

        assertNotNull(sportCar.getSpoiler());

    }

    @Test
    public void givenAutowiredAnnotation_WhenSetOnSetter_ThenDependencyIsNotNull() {

        ApplicationContext context = new AnnotationConfigApplicationContext("com.baeldung.firstarticle.dependencyinjectiontypes");
        RegularCar regularCar = (RegularCar) context.getBean("regularCar");

        assertNotNull(regularCar.getSpoiler());

    }

}
