package org.baeldung.spring43.depresolution;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

import static org.junit.Assert.assertNotNull;

@ContextConfiguration(classes = ObjectProviderConfiguration.class)
public class ObjectProviderIntegrationTest extends AbstractJUnit4SpringContextTests {

    @Autowired
    private FooService fooService;

    @Test
    public void whenArgumentIsObjectProvider_thenObjectProviderInjected() {

        assertNotNull(fooService.getRepository());

    }

}
