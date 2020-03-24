package com.baeldung.getbean;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import static org.junit.jupiter.api.Assertions.assertEquals;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class GetBeanByTypeWithConstructorParametersUnitTest {
    private ApplicationContext context;

    @BeforeAll
    void setup() {
        context = new AnnotationConfigApplicationContext(AnnotationConfig.class);
    }

    @Test
    void whenGivenExistingTypeAndValidParameters_thenShouldReturnRelatedBean() {
        Tiger tiger = context.getBean(Tiger.class, "Shere Khan");

        assertEquals("Shere Khan", tiger.getName());
    }
}
