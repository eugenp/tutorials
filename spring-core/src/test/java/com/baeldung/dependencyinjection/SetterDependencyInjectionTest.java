package com.baeldung.dependencyinjection;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.baeldung.dependencyinjection.model.LaptopSetterInject;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = SetterInjectConfig.class)
public class SetterDependencyInjectionTest {

    @Autowired
    private LaptopSetterInject laptopSetterInject;

    @Test
    public void testConstructBean() {
        assertTrue("Failed: Constructor Injection,String Property , Java Config", laptopSetterInject.getKeyboard()
            .getKeyConfiguration()
            .equals("QWERTY"));
        assertTrue("Failed: Constructor Injection,String Property , Java Config", laptopSetterInject.getMouse()
            .getMouseType()
            .equals("Wireless"));
    }

}
