package com.baeldung.concurrent;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = Spring5JUnit4ConcurrentIntegrationTest.SimpleConfiguration.class)
public class Spring5JUnit4ConcurrentIntegrationTest implements ApplicationContextAware, InitializingBean {

    @Configuration
    public static class SimpleConfiguration {
    }

    private ApplicationContext applicationContext;

    private boolean beanInitialized = false;

    @Override
    public final void afterPropertiesSet() throws Exception {
        this.beanInitialized = true;
    }

    @Override
    public final void setApplicationContext(final ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Test
    public final void verifyApplicationContextSet() throws InterruptedException {
        TimeUnit.SECONDS.sleep(2);
        assertNotNull("The application context should have been set due to ApplicationContextAware semantics.", this.applicationContext);
    }

    @Test
    public final void verifyBeanInitialized() throws InterruptedException {
        TimeUnit.SECONDS.sleep(2);
        assertTrue("This test bean should have been initialized due to InitializingBean semantics.", this.beanInitialized);
    }

}
