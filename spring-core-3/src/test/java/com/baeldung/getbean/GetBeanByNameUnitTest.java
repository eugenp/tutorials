package com.baeldung.getbean;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class GetBeanByNameUnitTest {
    private ApplicationContext context;

    @BeforeAll
    void setup() {
        context = new AnnotationConfigApplicationContext(AnnotationConfig.class);
    }

    @Test
    void whenGivenExistingBeanName_shouldReturnThatBean() {
        Object lion = context.getBean("lion");

        assertEquals(lion.getClass(), Lion.class);
    }

    @Test
    void whenGivenNonExistingBeanName_shouldThrowException() {
        assertThrows(NoSuchBeanDefinitionException.class, () -> context.getBean("non-existing"));
    }

    @Test
    void whenCastingToWrongType_thenShouldThrowException() {
        assertThrows(ClassCastException.class, () -> {
            Tiger tiger = (Tiger) context.getBean("lion");
        });
    }
}
