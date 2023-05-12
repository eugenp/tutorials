package com.baeldung.activeprofile;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.EnabledIf;

@SpringBootTest(classes = ActiveProfileApplication.class)
@EnabledIf(value = "#{environment.getActiveProfiles()[0] == 'prod'}", loadContext = true)
@ActiveProfiles(value = "prod")
public class ProdActiveProfileUnitTest {

    @Value("${profile.property.value}")
    private String propertyString;

    @Test
    void whenProdIsActive_thenValueShouldBeKeptFromApplicationProdYaml() {
        Assertions.assertEquals("This the the application-prod.yaml file", propertyString);
    }

}
