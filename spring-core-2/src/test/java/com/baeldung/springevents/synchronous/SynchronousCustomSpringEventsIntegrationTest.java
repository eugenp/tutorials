package com.baeldung.springevents.synchronous;

import static org.springframework.util.Assert.isTrue;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { SynchronousSpringEventsConfig.class }, loader = AnnotationConfigContextLoader.class)
public class SynchronousCustomSpringEventsIntegrationTest {

    @Autowired
    private CustomSpringEventPublisher publisher;
    @Autowired
    private AnnotationDrivenEventListener listener;
    @Autowired
    private ConfigurableApplicationContext context;

    @Before
    public void setup() {
        //Force publish of ContextStartedEvent so that the test case, testContextStartedEvent passes.
        context.start();
    }

    @Test
    public void testCustomSpringEvents() {
        isTrue(!listener.isHitCustomEventHandler(), "The value should be false");
        publisher.publishCustomEvent("Hello world!!");
        System.out.println("Done publishing synchronous custom event. ");
        isTrue(listener.isHitCustomEventHandler(), "Now the value should be changed to true");
    }

    @Test
    public void testGenericSpringEvent() {
        isTrue(!listener.isHitSuccessfulEventHandler(), "The initial value should be false");
        publisher.publishGenericEvent("Hello world!!!", true);
        isTrue(listener.isHitSuccessfulEventHandler(), "Now the value should be changed to true");
    }

    @Test
    public void testGenericSpringEventNotProcessed() {
        isTrue(!listener.isHitSuccessfulEventHandler(), "The initial value should be false");
        publisher.publishGenericEvent("Hello world!!!", false);
        isTrue(!listener.isHitSuccessfulEventHandler(), "The value should still be false");
    }

    @Test
    public void testContextStartedEvent() {
        isTrue(listener.isHitContextStartedHandler(), "Start should be called once");
    }
}
