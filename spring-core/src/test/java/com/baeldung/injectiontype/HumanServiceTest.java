package com.baeldung.injectiontype;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import com.baeldung.configuration.ApplicationContextTestInjectName;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader = AnnotationConfigContextLoader.class, classes = ApplicationContextTestInjectName.class)
public class HumanServiceTest {
    
    ApplicationContext context =null;
    HumanService humanService = null;
    
    @Before
    public void init(){
        context = new AnnotationConfigApplicationContext(HumanAppConfig.class);
        humanService = (HumanService) context.getBean(HumanService.class);
    }

    @Test
    public void testPerformAction() {
        Assert.assertNotNull(humanService);
        Assert.assertNotNull(humanService.getName());
        Assert.assertEquals("Sarf",humanService.getName());
        humanService.performAction();
    }

}
