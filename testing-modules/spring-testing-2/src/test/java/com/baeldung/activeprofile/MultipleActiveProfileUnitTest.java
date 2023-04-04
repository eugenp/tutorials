package com.baeldung.activeprofile;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.EnabledIf;

@SpringBootTest(classes = ActiveProfileApplication.class)
@EnabledIf(value = "#{{'dev', 'prod'}.contains(environment.getActiveProfiles()[0])}", loadContext = true)
public class MultipleActiveProfileUnitTest {
    @Value("${profile.property.value}")
    private String propertyString;

    @Test
    void whenDevIsActive_ThenValueShouldBeKeptFromApplicationDevYaml() {
        ;
        Assertions.assertEquals("This the the application-dev.yaml file", propertyString, propertyString);
    }
}
