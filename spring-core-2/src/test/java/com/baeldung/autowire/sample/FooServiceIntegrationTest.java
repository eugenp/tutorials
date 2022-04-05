package com.baeldung.autowire.sample;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = AppConfig.class, loader = AnnotationConfigContextLoader.class)
public class FooServiceIntegrationTest {

    @Autowired
    FooService fooService;

    @Test
    public void whenFooFormatterType_thenReturnFoo() {
        Assert.assertEquals("foo", fooService.doStuff());
    }
}
