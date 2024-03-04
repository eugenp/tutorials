package com.baeldung.testpropertysource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = ClassUsingProperty.class)
@TestPropertySource(
    locations = "/other-location.properties",
    properties = """
        baeldung.testpropertysource.one=one
        baeldung.testpropertysource.two=two
    """)
public class MultiplePropertiesInPropertySourceTextBlockIntegrationTest {

    @Autowired
    ClassUsingProperty classUsingProperty;

    @Test
    public void givenAMultilinePropertySource_whenVariableOneRetrieved_thenValueInPropertyAnnotationIsReturned() {
        String output = classUsingProperty.retrievePropertyOne();

        assertThat(output).isEqualTo("one");
    }

    @Test
    public void givenAMultilinePropertySource_whenVariableTwoRetrieved_thenValueInPropertyAnnotationIsReturned() {
        String output = classUsingProperty.retrievePropertyTwo();

        assertThat(output).isEqualTo("two");
    }
}
