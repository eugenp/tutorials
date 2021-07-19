package com.baeldung.getbean;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.BeanNotOfRequiredTypeException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class GetBeanByNameAndTypeUnitTest {
    private ApplicationContext context;

    @BeforeAll
    void setup() {
        context = new AnnotationConfigApplicationContext(AnnotationConfig.class);
    }

    @Test
    void whenSpecifiedMatchingNameAndType_thenShouldReturnRelatedBean() {
        Lion lion = context.getBean("lion", Lion.class);

        assertEquals("Hardcoded lion name", lion.getName());
    }

    @Test
    void whenSpecifiedNotMatchingNameAndType_thenShouldThrowException() {
        assertThrows(BeanNotOfRequiredTypeException.class, () -> context.getBean("lion", Tiger.class));
    }
}
