package com.baeldung.mbassador;

import net.engio.mbassy.bus.MBassador;
import net.engio.mbassy.listener.Handler;
import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.atomic.AtomicBoolean;

import static org.awaitility.Awaitility.await;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertNotNull;

public class MBassadorAsyncDispatchUnitTest {

    private MBassador dispatcher = new MBassador<String>();
    private String testString;
    private AtomicBoolean ready = new AtomicBoolean(false);

    @Before
    public void prepareTests() {
        dispatcher.subscribe(this);
    }

    @Test
    public void whenAsyncDispatched_thenMessageReceived() {
        dispatcher.post("foobar").asynchronously();
        await().untilAtomic(ready, equalTo(true));
        assertNotNull(testString);
    }

    @Handler
    public void handleStringMessage(String message) {
        this.testString = message;
        ready.set(true);
    }
}
