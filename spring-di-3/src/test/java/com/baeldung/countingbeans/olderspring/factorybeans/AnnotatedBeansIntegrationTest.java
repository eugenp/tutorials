package com.baeldung.countingbeans.olderspring.factorybeans;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {MyConfigurationBean.class, AnnotatedBeansComponent.class})
public class AnnotatedBeansIntegrationTest {

    @Autowired
    AnnotatedBeansComponent annotatedBeansComponent;

    @Test
    void whenBeanUtilsGetBeansWithAnnotation_ThenShouldListAnnotatedBean() {
        try (AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(MyConfigurationBean.class)) {
            List<String> result = BeanUtils.getBeansWithAnnotation(applicationContext, MyCustomAnnotation.class);
            assertEquals(1, result.size());
            assertEquals("myService", result.get(0));
        }
    }

    @Test
    void whenBeanUtilsGetBeansWithAnnotationStreamVersion_ThenShouldListAnnotatedBean() {
        try (AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(MyConfigurationBean.class)) {
            List<String> result = BeanUtils.getBeansWithAnnotation(applicationContext, MyCustomAnnotation.class);
            assertEquals(1, result.size());
            assertEquals("myService", result.get(0));

        }
    }

    @Test
    void whenAnnotatedBeansComponentGetBeansWithAnnotation_ThenShouldListAnnotatedBean() {
        List<String> result = annotatedBeansComponent.getBeansWithAnnotation(MyCustomAnnotation.class);
        assertEquals(1, result.size());
        assertEquals("myService", result.get(0));
    }

}