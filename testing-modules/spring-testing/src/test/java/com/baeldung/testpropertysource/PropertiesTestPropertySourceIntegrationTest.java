package com.baeldung.testpropertysource;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = ClassUsingProperty.class)
@TestPropertySource(locations = "/other-location.properties", properties = "baeldung.testpropertysource.one=other-properties-value")
public class PropertiesTestPropertySourceIntegrationTest {

    @Autowired
    ClassUsingProperty classUsingProperty;

    @Test
    public void givenDefaultTestPropertySource_whenVariableOneRetrieved_thenValueInDefaultFileReturned() {
        String output = classUsingProperty.retrievePropertyOne();

        assertThat(output).isEqualTo("other-properties-value");
    }
}
