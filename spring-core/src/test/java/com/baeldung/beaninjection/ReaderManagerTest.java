package com.baeldung.beaninjection;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = ReaderApplicationConfig.class)
public class ReaderManagerTest {

    @Autowired
    private ApplicationContext context;

    @Test
    public void testAutowiredAnnotation_WhenConstructorAndSetter_ThenInjected() {
        ReaderManager service = context.getBean(ReaderManager.class);
        assertNotNull(service.getFtpReader());
        assertNotNull(service.getFileReader());
    }
}
