package com.baeldung.overrideproperties;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.baeldung.overrideproperties.resolver.PropertySourceResolver;

@RunWith(SpringRunner.class)
@SpringBootTest
@EnableWebMvc
public class TestResourcePropertySourceResolverIntegrationTest {

    @Autowired
    private PropertySourceResolver propertySourceResolver;

    @Test
    public void shouldTestResourceFile_overridePropertyValues() {
        final String firstProperty = propertySourceResolver.getFirstProperty();
        final String secondProperty = propertySourceResolver.getSecondProperty();

        assertEquals("file", firstProperty);
        assertEquals("file", secondProperty);
    }

}