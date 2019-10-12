package org.baeldung.springevents.synchronous;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import static org.springframework.util.Assert.isTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { SynchronousSpringEventsConfig.class }, loader = AnnotationConfigContextLoader.class)
public class GenericAppEventListenerIntegrationTest {

    @Autowired
    private CustomSpringEventPublisher publisher;
    @Autowired
    private GenericSpringEventListener listener;

    @Test
    public void testGenericSpringEvent() {
        isTrue(!listener.isHitEventHandler(), "The initial value should be false");
        publisher.publishGenericAppEvent("Hello world!!!");
        isTrue(listener.isHitEventHandler(), "Now the value should be changed to true");
    }

}