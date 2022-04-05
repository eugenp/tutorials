package com.baeldung.di.spring;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class BeanInjectionIntegrationTest {

    private ApplicationContext applicationContext;

    @Before
    public void setUp() throws Exception {
        applicationContext = new ClassPathXmlApplicationContext("com.baeldung.di.spring.xml");
    }

    @Test
    public void singletonBean_getBean_returnsSingleInstance() {
        final IndexApp indexApp1 = applicationContext.getBean("indexApp", IndexApp.class);
        final IndexApp indexApp2 = applicationContext.getBean("indexApp", IndexApp.class);
        assertEquals(indexApp1, indexApp2);
    }

    @Test
    public void getBean_returnsInstance() {
        final IndexApp indexApp = applicationContext.getBean("indexApp", IndexApp.class);
        assertNotNull(indexApp);
    }

}
