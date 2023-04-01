package com.baeldung.junit5.activeprofile;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;

@SpringBootTest
public class DevActiveProfileUnitTest {

    @Value("${profile.property.value}")
    private String propertyString;

    @Test
    void whenDevIsActive_ThenValueShouldBeKeptFromApplicationDevYaml() {
        ;
        Assertions.assertEquals("This the the application-dev.yaml file", propertyString, propertyString);
    }
}
