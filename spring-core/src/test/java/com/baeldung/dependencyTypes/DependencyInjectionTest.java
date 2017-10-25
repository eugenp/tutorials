package com.baeldung.dependencyTypes;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
public class DependencyInjectionTest extends AbstractJUnit4SpringContextTests{
    
    @Configuration
    @ComponentScan("com.baeldung.dependencyTypes")
    public static class SpringConfig {

    }
    
    @Test
    public void testConstructorInjection() {
        DesktopComputer desktopComputer = applicationContext.getBean(DesktopComputer.class);
        
        assertNotNull(desktopComputer);
        assertNotNull(desktopComputer.getCaze());
        assertNotNull(desktopComputer.getKeyboard());
        assertNotNull(desktopComputer.getMonitor());
        assertNotNull(desktopComputer.getMouse());
        
        assertEquals(desktopComputer.getCaze().getClass(),IBMCase.class);
        assertEquals(desktopComputer.getKeyboard().getClass(),IBMKeyboard.class);
        assertEquals(desktopComputer.getMonitor().getClass(),LGMonitor.class);
        assertEquals(desktopComputer.getMouse().getClass(),LogiTechMouse.class);
        
    }
    
    @Test
    public void testSetterInjectionOnVariable() {
        DesktopComputer1 desktopComputer1 = applicationContext.getBean(DesktopComputer1.class);
        
        assertNotNull(desktopComputer1);
        assertNotNull(desktopComputer1.getCaze());
        assertNotNull(desktopComputer1.getKeyboard());
        assertNotNull(desktopComputer1.getMonitor());
        assertNotNull(desktopComputer1.getMouse());
        
        assertEquals(desktopComputer1.getCaze().getClass(),IBMCase.class);
        assertEquals(desktopComputer1.getKeyboard().getClass(),IBMKeyboard.class);
        assertEquals(desktopComputer1.getMonitor().getClass(),LGMonitor.class);
        assertEquals(desktopComputer1.getMouse().getClass(),LogiTechMouse.class);
        
    }
    
    @Test
    public void testConstructorInjectionOnSetMethod() {
        DesktopComputer2 desktopComputer2 = applicationContext.getBean(DesktopComputer2.class);
        
        assertNotNull(desktopComputer2);
        assertNotNull(desktopComputer2.getCaze());
        assertNotNull(desktopComputer2.getKeyboard());
        assertNotNull(desktopComputer2.getMonitor());
        assertNotNull(desktopComputer2.getMouse());
        
        assertEquals(desktopComputer2.getCaze().getClass(),IBMCase.class);
        assertEquals(desktopComputer2.getKeyboard().getClass(),IBMKeyboard.class);
        assertEquals(desktopComputer2.getMonitor().getClass(),LGMonitor.class);
        assertEquals(desktopComputer2.getMouse().getClass(),LogiTechMouse.class);
        
    }

}
