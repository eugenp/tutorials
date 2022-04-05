package com.baeldung.spring43.ctor;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

import static org.junit.Assert.assertNotNull;

@ContextConfiguration(classes = { FooRepositoryConfiguration.class, FooServiceConfiguration.class })
public class ConfigurationConstructorInjectionIntegrationTest extends AbstractJUnit4SpringContextTests {

    @Autowired
    public FooService fooService;

    @Test
    public void whenSingleCtorInConfiguration_thenContextLoadsNormally() {
        assertNotNull(fooService.getRepository());
    }

}
