package com.baeldung.getbean;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.UnsatisfiedDependencyException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class GetBeanByNameWithConstructorParametersUnitTest {
    private ApplicationContext context;

    @BeforeAll
    void setup() {
        context = new AnnotationConfigApplicationContext(AnnotationConfig.class);
    }

    @Test
    void whenGivenCorrectName_thenShouldReturnBeanWithSpecifiedName() {
        Tiger tiger = (Tiger) context.getBean("tiger", "Siberian");

        assertEquals("Siberian", tiger.getName());
    }

    @Test
    void whenGivenCorrectNameOrAlias_shouldReturnBeanWithSpecifiedName() {
        Tiger tiger = (Tiger) context.getBean("tiger", "Siberian");
        Tiger secondTiger = (Tiger) context.getBean("tiger", "Striped");

        assertEquals("Siberian", tiger.getName());
        assertEquals("Striped", secondTiger.getName());
    }

    @Test
    void whenNoArgumentSpecifiedForPrototypeBean_thenShouldThrowException() {
        assertThrows(UnsatisfiedDependencyException.class, () -> {
            Tiger tiger = (Tiger) context.getBean("tiger");
        });
    }
}
