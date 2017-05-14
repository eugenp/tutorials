package org.baeldung.beaninjection;

import org.baeldung.beaninjection.config.BeanInjectionConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { BeanInjectionConfig.class }, loader = AnnotationConfigContextLoader.class)
public class DeployServiceTest {
    
    @Autowired
    private DeployService deployService;

    @Test
    public void whenSendingNotification_thenMessageIsDelivered() {
        deployService.deploy();
    }
    
}
