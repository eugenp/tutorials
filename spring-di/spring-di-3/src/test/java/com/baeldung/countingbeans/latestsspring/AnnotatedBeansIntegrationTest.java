package com.baeldung.countingbeans.latestsspring;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class AnnotatedBeansIntegrationTest {

    /**
     * Note : this test fails with any spring version < 2.2
     * Before, the getBeansWithAnnotation method was not checking the beans created via factory method
     * Please find the change here : https://github.com/spring-projects/spring-framework/commit/e0fe32af05ac525ef5e11c3ac5195a08759bb85e
     */
    @Test
    void whenApplicationContextStarted_ThenShouldDetectAllAnnotatedBeans() {
        try (AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext( MyComponent.class, MyConfigurationBean.class )) {
            Map<String,Object> beans = applicationContext.getBeansWithAnnotation(MyCustomAnnotation.class);
            assertEquals(2, beans.size());
            assertTrue(beans.keySet().containsAll(Arrays.asList("myComponent", "myService")));
        }
    }

}