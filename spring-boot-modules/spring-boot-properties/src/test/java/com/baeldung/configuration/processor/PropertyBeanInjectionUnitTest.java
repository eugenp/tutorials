package com.baeldung.configuration.processor;

import org.junit.jupiter.api.*;
import org.junit.runner.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.test.context.*;
import org.springframework.test.context.*;
import org.springframework.test.context.junit4.*;

@RunWith(SpringRunner.class)
@TestPropertySource("/configuration-processor.properties")
@SpringBootTest(classes = DemoApplication.class)
class PropertyBeanInjectionUnitTest {

    @Autowired
    private PropertyBeanInjection propertyBeanInjection;

    @Test
    void checkThatCustomPropertiesHaveTheCorrectValueFromPropertiesFile() {
        Assertions.assertEquals("www.abc.test.com", propertyBeanInjection.getUrl());
        Assertions.assertEquals(2000, propertyBeanInjection.getTimeoutInMilliseconds());
    }

}
