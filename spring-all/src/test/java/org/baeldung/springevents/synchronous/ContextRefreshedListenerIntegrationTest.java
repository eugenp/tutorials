package org.baeldung.springevents.synchronous;

import org.baeldung.springevents.synchronous.SynchronousSpringEventsConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { SynchronousSpringEventsConfig.class }, loader = AnnotationConfigContextLoader.class)
public class ContextRefreshedListenerIntegrationTest {

    @Test
    public void testContextRefreshedListener() throws InterruptedException {
        System.out.println("Test context re-freshed listener.");
    }
}