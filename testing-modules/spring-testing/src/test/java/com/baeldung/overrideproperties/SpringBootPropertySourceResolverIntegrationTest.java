package com.baeldung.overrideproperties;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.baeldung.overrideproperties.resolver.PropertySourceResolver;

@RunWith(SpringRunner.class)
@SpringBootTest(properties = { "example.firstProperty=annotation" })
@EnableWebMvc
public class SpringBootPropertySourceResolverIntegrationTest {

    @Autowired
    private PropertySourceResolver propertySourceResolver;

    @Test
    public void shouldSpringBootTestAnnotation_overridePropertyValues() {
        final String firstProperty = propertySourceResolver.getFirstProperty();
        final String secondProperty = propertySourceResolver.getSecondProperty();

        Assert.assertEquals("annotation", firstProperty);
        Assert.assertEquals("file", secondProperty);
    }

}