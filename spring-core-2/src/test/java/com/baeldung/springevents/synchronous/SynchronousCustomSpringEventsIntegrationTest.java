package com.baeldung.springevents.synchronous;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import static org.springframework.util.Assert.isTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { SynchronousSpringEventsConfig.class }, loader = AnnotationConfigContextLoader.class)
public class SynchronousCustomSpringEventsIntegrationTest {

    @Autowired
    private CustomSpringEventPublisher publisher;
    @Autowired
    private AnnotationDrivenEventListener listener;

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

    @Ignore("fix me")
    @Test
    public void testContextStartedEvent() {
        isTrue(listener.isHitContextStartedHandler(), "Start should be called once");
    }
}
