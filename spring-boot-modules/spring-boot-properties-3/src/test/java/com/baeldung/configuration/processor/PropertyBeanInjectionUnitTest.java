package com.baeldung.configuration.processor;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@TestPropertySource("/configuration-processor.properties")
@SpringBootTest(classes = DemoApplication.class)
class PropertyBeanInjectionUnitTest {

    @Autowired
    private PropertyBeanInjection propertyBeanInjection;

    @Test
    void checkThatJdbcPropertiesHaveTheCorrectValueFromPropertiesFile() {
        Assertions.assertEquals("jdbc:postgresql:/localhost:5432", propertyBeanInjection.getJdbcUrl());
    }

    @Test
    void checkThatCustomPropertiesHaveTheCorrectValueFromPropertiesFile() {
        Assertions.assertEquals("www.abc.test.com", propertyBeanInjection.getUrl());
        Assertions.assertEquals(2000, propertyBeanInjection.getTimeoutInMilliseconds());
    }

}
