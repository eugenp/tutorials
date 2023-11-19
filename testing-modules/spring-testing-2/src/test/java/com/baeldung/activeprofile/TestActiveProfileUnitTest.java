package com.baeldung.activeprofile;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(classes = ActiveProfileApplication.class)
@ActiveProfiles(value = "test")
public class TestActiveProfileUnitTest {

    @Value("${profile.property.value}")
    private String propertyString;

    @Test
    void whenTestIsActive_thenValueShouldBeKeptFromApplicationTestYaml() {
        Assertions.assertEquals("This the the application-test.yaml file", propertyString);
    }
}
