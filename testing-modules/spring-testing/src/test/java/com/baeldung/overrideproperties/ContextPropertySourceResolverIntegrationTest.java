package com.baeldung.overrideproperties;

import com.baeldung.overrideproperties.resolver.PropertySourceResolver;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
@ExtendWith(SpringExtension.class)
@ContextConfiguration(initializers = PropertyOverrideContextInitializer.class, classes = Application.class)
public class ContextPropertySourceResolverIntegrationTest {

    @Autowired private PropertySourceResolver propertySourceResolver;

    @Test
    public void shouldContext_overridePropertyValues() {
        final String firstProperty = propertySourceResolver.getFirstProperty();
        final String secondProperty = propertySourceResolver.getSecondProperty();

        assertEquals(PropertyOverrideContextInitializer.PROPERTY_FIRST_VALUE, firstProperty);
        assertEquals("contextFile", secondProperty);
    }

}