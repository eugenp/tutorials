package com.baeldung.beaninjection;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:injectiontypes.xml")
public class FtpReaderTest {

    @Autowired
    private ApplicationContext context;

    @Test
    public void testXMLBeanConfig_WhenConstructorArguments_ThenInjected() {
        FtpReader service = context.getBean(FtpReader.class);
        assertNotNull(service.getFtpHost());
        assertNotNull(service.getFtpPort());
    }
}
