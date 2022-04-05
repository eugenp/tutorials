package com.baeldung.factorymethod;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/factorymethod/instance-bar-config.xml")
public class InstanceBarFactoryIntegrationTest {

    @Autowired
    private Bar instance;
    
    @Test
    public void givenValidInstanceFactoryConfig_whenCreateInstance_thenNameIsCorrect() {
        assertNotNull(instance);
        assertEquals("someName", instance.getName());
    }
}
