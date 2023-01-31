package com.baeldung.callbackfunctions;

import org.junit.Test;
import org.mockito.Mockito;
import com.baeldung.callbackfunctions.EventListener;
import com.baeldung.callbackfunctions.asynchronous.AsynchronousEventConsumer;
import com.baeldung.callbackfunctions.asynchronous.AsynchronousEventListenerImpl;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class AsynchronousCallbackUnitTest {

    @Test
    public void whenCallbackIsInvokedAsynchronously_shouldRunAsynchronousOperation(){
        EventListener listener = Mockito.mock(AsynchronousEventListenerImpl.class);
        AsynchronousEventConsumer asynchronousEventListenerConsumer = new AsynchronousEventConsumer(listener);
        asynchronousEventListenerConsumer.doAsynchronousOperation();

        verify(listener, times(1)).onTrigger();
    }
}
