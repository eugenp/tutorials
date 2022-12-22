package com.baeldung.countingbeans.olderspring.qualifier;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {MyComponent.class, MyConfigurationBean.class})
public class AnnotatedBeansIntegrationTest {

    @Autowired
    @MyCustomAnnotation
    private List<Object> annotatedBeans;

    @Test
    void whenAutowiring_ThenShouldDetectAllAnnotatedBeans() {
        assertEquals(2, annotatedBeans.size());
        List<String> classNames = annotatedBeans.stream()
            .map(Object::getClass)
            .map(Class::getName)
            .map(s -> s.substring(s.lastIndexOf(".") + 1))
            .collect(Collectors.toList());
        assertTrue(classNames.containsAll(Arrays.asList("MyComponent", "MyService")));
    }

}
