package com.baeldung.mbassador;

import net.engio.mbassy.bus.MBassador;
import net.engio.mbassy.listener.Handler;
import net.engio.mbassy.listener.Invoke;
import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.atomic.AtomicBoolean;

import static org.awaitility.Awaitility.await;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertFalse;

public class MBassadorAsyncInvocationUnitTest {

    private MBassador dispatcher = new MBassador<Integer>();

    private Integer testInteger;
    private String invocationThreadName;
    private AtomicBoolean ready = new AtomicBoolean(false);

    @Before
    public void prepareTests() {
        dispatcher.subscribe(this);
    }

    @Test
    public void whenHandlerAsync_thenHandled() {

        dispatcher.post(42).now();

        await().untilAtomic(ready, equalTo(true));
        assertNotNull(testInteger);
        assertFalse(Thread.currentThread().getName().equals(invocationThreadName));
    }

    @Handler(delivery = Invoke.Asynchronously)
    public void handleIntegerMessage(Integer message) {
        this.invocationThreadName = Thread.currentThread().getName();
        this.testInteger = message;
        ready.set(true);
    }
}
