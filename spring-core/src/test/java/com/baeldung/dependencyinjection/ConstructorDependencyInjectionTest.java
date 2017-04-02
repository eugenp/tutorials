package com.baeldung.dependencyinjection;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.baeldung.dependencyinjection.model.LaptopContructorInject;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = ConstructorInjectConfig.class)
public class ConstructorDependencyInjectionTest {
    
    @Autowired
    private LaptopContructorInject laptopContructorInject;
    
    
    @Test
    public void testConstructBean() {
        assertTrue("Failed: Constructor Injection,String Property , Java Config", laptopContructorInject.getKeyboard().getKeyConfiguration()
            .equals("DVORAK"));
        assertTrue("Failed: Constructor Injection,String Property , Java Config", laptopContructorInject.getMouse().getMouseType()
            .equals("Wired"));
    }

}
