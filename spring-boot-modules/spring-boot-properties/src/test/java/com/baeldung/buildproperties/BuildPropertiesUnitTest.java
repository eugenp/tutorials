package com.baeldung.buildproperties;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.info.BuildProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class BuildPropertiesUnitTest {
    @Autowired
    private BuildProperties buildProperties;

    @Test
    void givenBuildPropertiesBean_WhenFetchDefaultBuildProperties_ThenGetValidValues() {
        Assertions.assertEquals("spring-boot-properties", buildProperties.getArtifact());
        Assertions.assertEquals("com.baeldung", buildProperties.getGroup());
        Assertions.assertEquals("0.0.1-SNAPSHOT", buildProperties.getVersion());
    }

    @Test
    void givenBuildPropertiesBean_WhenFetchCustomBuildProprties_ThenGetValidValues() {
        Assertions.assertEquals("123", buildProperties.get("custom.value"));
        Assertions.assertNotNull(buildProperties.get("java.version"));
        Assertions.assertEquals("Spring Boot Properties Module", buildProperties.get("description"));
    }
}
