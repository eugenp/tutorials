package com.baeldung.beanfactorypostprocessor;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = PropertiesWithBeanFactoryPostProcessor.class)
public class PropertiesWithBeanFactoryPostProcessorUnitTest {

    @Autowired
    private String presentationValueFromComponentAnnotation;
    @Autowired
    private String presentationValueFromBeanAnnotation;

    @Test
    void givenBeanFactoryPostProcessor_whenCreatedWithBeanAnnotation_thenPropertyIsRead() {
        assertThat(presentationValueFromBeanAnnotation).contains("\"Baeldung\" ${HELLO_BAELDUNG}. Java is installed in the folder:");
    }

    @Test
    void givenBeanFactoryPostProcessor_whenCreatedWithComponentAnnotation_thenPropertyIsRead() {
        assertThat(presentationValueFromComponentAnnotation).contains("\"Baeldung\" ${HELLO_BAELDUNG}. Java is installed in the folder:");
    }
}
