package com.baeldung.dependencyinjectiontypes;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.lang.reflect.Field;

import static org.junit.Assert.assertNotNull;

public class PizzaServiceTest {

    @Test
    public void whenInstantiated_thenFieldsPopulated() throws Exception {
        // Arrange
        ApplicationContext appContext = new AnnotationConfigApplicationContext(getClass().getPackage().getName());

        // Act
        PizzaService instance = appContext.getBean(PizzaService.class);

        // Assert
        assertNotNull("PizzaService bean should be defined", instance);

        for (Field field : PizzaService.class.getDeclaredFields()) {
            assertNotNull(field);

            // For assertion purposes
            field.setAccessible(true);

            Object fieldRef = field.get(instance);
            assertNotNull("Field reference '" + field.getName() + "' should've been populated", fieldRef);
        }
    }
}
