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
    void whenGivenCorrectNameOrAlias_shouldReturnBeanWithSpecifiedName() {
        Tiger tiger = (Tiger) context.getBean("tiger", "Cutie");
        Tiger tigerSecond = (Tiger) context.getBean("tiger", "Striped");
        Tiger tigerViaAlias = (Tiger) context.getBean("kitty", "Siberian");
        assertEquals("Cutie", tiger.getName());
        assertEquals("Striped", tigerSecond.getName());
        assertEquals("Siberian", tigerViaAlias.getName());
    }

    @Test
    void whenNoArgumentSpecifiedForPrototypeBean_thenShouldThrowException() {
        assertThrows(UnsatisfiedDependencyException.class, () -> {
            Tiger tiger = (Tiger) context.getBean("tiger");
        });
    }
}
