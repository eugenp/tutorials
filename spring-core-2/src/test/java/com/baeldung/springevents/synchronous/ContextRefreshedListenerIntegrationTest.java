package com.baeldung.springevents.synchronous;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import static org.springframework.util.Assert.isTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { SynchronousSpringEventsConfig.class }, loader = AnnotationConfigContextLoader.class)
public class ContextRefreshedListenerIntegrationTest {

    @Autowired
    private ContextRefreshedListener listener;

    @Test
    public void testContextRefreshedListener() {
        System.out.println("Test context re-freshed listener.");
        isTrue(listener.isHitContextRefreshedHandler(), "Refresh should be called once");
    }
}