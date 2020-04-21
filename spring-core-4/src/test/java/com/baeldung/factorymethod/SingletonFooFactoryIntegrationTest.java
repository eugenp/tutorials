package com.baeldung.factorymethod;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/factorymethod/static-foo-config.xml")
public class SingletonFooFactoryIntegrationTest {

    @Autowired
    private Foo singleton;
    
    @Test
    public void givenValidStaticFactoryConfig_whenCreateInstance_thenInstanceIsNotNull() {
        assertNotNull(singleton);
    }
}
