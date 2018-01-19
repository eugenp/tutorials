package com.baeldung.dependencyinjectiontypeseval;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class DependencyInjectionTest {
    @Test
    public void givenXMLConfig_WhenSetOnSetter_ThenDependencyValid() {

        try (ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("springconfig-eval.xml")) {
            LaptopWithSetter laptop = (LaptopWithSetter) context.getBean("LaptopWithSetterBean");
            assertTrue(laptop.getDetails()
                .equalsIgnoreCase("Nvidia graphics card"));
        }
    }

    @Test
    public void givenXMLConfig_WhenSetOnConstructor_ThenDependencyValid() {

        try (ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("springconfig-eval.xml")) {
            LaptopWithConstructor laptop = (LaptopWithConstructor) context.getBean("LaptopWithConstructorBean");
            assertTrue(laptop.getDetails()
                .equalsIgnoreCase("Nvidia graphics card"));

        }
    }

    @Test
    public void givenAutowiredAnnotation_WhenSetOnSetter_ThenDependencyValid() {

        try (AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class)) {
            LaptopWithSetterAnnotation laptop = context.getBean(LaptopWithSetterAnnotation.class);

            assertTrue(laptop.getDetails()
                .equalsIgnoreCase("Nvidia graphics card"));
        }
    }

    @Test
    public void givenAutowiredAnnotation_WhenSetOnConstructor_ThenDependencyValid() {

        try (AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class)) {
            LaptopWithConstructorAnnotation laptop = context.getBean(LaptopWithConstructorAnnotation.class);

            assertTrue(laptop.getDetails()
                .equalsIgnoreCase("Nvidia graphics card"));
        }
    }
}
