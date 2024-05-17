package com.baeldung.beanfactorypostprocessor;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = { PropertiesWithBeanFactoryPostProcessor.class, PropertiesWithBeanFactoryPostProcessorConfig.class })
public class PropertiesWithBeanFactoryPostProcessorUnitTest {

    @Autowired
    private String tutorialTitleFromComponentAnnotation;
    @Autowired
    private String tutorialTitleFromBeanAnnotation;

    @Test
    void givenBeanFactoryPostProcessor_whenCreatedWithBeanAnnotation_thenPropertiesAreRead() {
        assertThat(tutorialTitleFromBeanAnnotation).isEqualTo("Baeldung: Properties in BeanFactoryPostProcessor");
    }

    @Test
    void givenBeanFactoryPostProcessor_whenCreatedWithComponentAnnotation_thenPropertiesAreRead() {
        assertThat(tutorialTitleFromComponentAnnotation).isEqualTo("Baeldung - Properties in BeanFactoryPostProcessor");
    }
}
