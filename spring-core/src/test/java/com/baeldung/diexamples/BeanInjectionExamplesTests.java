package com.baeldung.diexamples;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import static org.junit.Assert.assertEquals;

@RunWith(JUnit4.class)
public class BeanInjectionExamplesTests
{
    @Test
    public void whenConstructorInjected_thenComputerReadsSSDDisk(){
        Computer computer = getContext().getBean(Computer.class);

        assertEquals("Getting bytes from SSD, real fast!", computer.start());
    }

    @Test
    public void whenSetterInjected_thenTabletReadsSSDDisk(){
        Tablet tablet = getContext().getBean(Tablet.class);

        assertEquals("Getting bytes from SSD, real fast!", tablet.start());
    }

    @Test
    public void whenFieldInjected_thenPhoneReadsSSDDisk(){
        Phone phone = getContext().getBean(Phone.class);

        assertEquals("Getting bytes from SSD, real fast!", phone.start());
    }

    private AnnotationConfigApplicationContext getContext(){
        return new AnnotationConfigApplicationContext(AppConfig.class);
    }
}
