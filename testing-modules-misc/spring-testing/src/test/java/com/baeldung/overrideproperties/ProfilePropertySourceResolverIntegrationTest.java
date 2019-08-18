package com.baeldung.overrideproperties;

import com.baeldung.overrideproperties.resolver.PropertySourceResolver;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class ProfilePropertySourceResolverIntegrationTest {

    @Autowired private PropertySourceResolver propertySourceResolver;

    @Test
    public void shouldProfiledProperty_overridePropertyValues() {
        final String firstProperty = propertySourceResolver.getFirstProperty();
        final String secondProperty = propertySourceResolver.getSecondProperty();

        assertEquals("profile", firstProperty);
        assertEquals("file", secondProperty);
    }

}