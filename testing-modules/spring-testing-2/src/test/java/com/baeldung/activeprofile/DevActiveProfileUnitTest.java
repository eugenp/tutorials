package com.baeldung.activeprofile;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = ActiveProfileApplication.class)
public class DevActiveProfileUnitTest {

    @Value("${profile.property.value}")
    private String propertyString;

    @Test
    void whenDevIsActive_thenValueShouldBeKeptFromApplicationYaml() {
        Assertions.assertEquals("This the the application.yaml file", propertyString);
    }
}
