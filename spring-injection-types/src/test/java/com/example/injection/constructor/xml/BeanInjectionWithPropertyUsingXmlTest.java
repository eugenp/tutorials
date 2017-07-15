package com.example.injection.constructor.xml;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@ContextConfiguration("classpath:constructor-injection.xml")
public class BeanInjectionWithPropertyUsingXmlTest {
    
    @Autowired
    private PushButton pushButton;
    
    @Autowired
    private DoorBell doorBell;
    
    @Test
    public void testBeanInjection() {
        pushButton.press();
        pushButton.press();
        assertEquals(doorBell.getRingCount(), 2);
    }
    
}
