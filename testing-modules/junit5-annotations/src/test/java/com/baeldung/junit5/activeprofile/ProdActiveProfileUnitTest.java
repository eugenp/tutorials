package com.baeldung.junit5.activeprofile;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.EnabledIf;

@SpringBootTest
@EnabledIf(value = "#{environment.getActiveProfiles()[0] == 'prod'}", loadContext = true)
@ActiveProfiles(value = "prod")

public class ProdActiveProfileUnitTest {

    @Value("${profile.property.value}")
    private String propertyString;

    @Autowired
    private

    @Test void whenProdIsActive_ThenValueShouldBeKeptFromApplicationYaml() {
        Assertions.assertEquals("This the the application.yaml file", propertyString, propertyString);
    }

    @Test
    void whenProdIsASystemVariable_ThenTestShouldRun() {
        Assertions.assertEquals("This the the application.yaml file", propertyString, propertyString);
    }

}
