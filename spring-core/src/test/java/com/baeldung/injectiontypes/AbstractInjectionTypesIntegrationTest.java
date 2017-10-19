package com.baeldung.injectiontypes;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertSame;

@RunWith(SpringJUnit4ClassRunner.class)
@Ignore
public abstract class AbstractInjectionTypesIntegrationTest {

    @Autowired
    Dependency dependency;

    @Autowired
    ServiceThroughConstructor serviceThroughConstructor;

    @Autowired
    ServiceThroughSetter serviceThroughSetter;

    @Test
    public void whenContextIsProperlyConfigured_thenConstructorInjectionWorksAsExpected() {
        assertSame(serviceThroughConstructor.getDependency(), dependency);
    }

    @Test
    public void whenContextIsProperlyConfigured_thenSetterInjectionWorksAsExpected() {
        assertSame(serviceThroughSetter.getDependency(), dependency);
    }
}