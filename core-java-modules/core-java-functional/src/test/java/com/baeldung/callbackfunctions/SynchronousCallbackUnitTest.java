package callbackFunctions;

import org.junit.jupiter.api.Test;
import com.baeldung.callbackfunctions.EventListener;
import com.baeldung.callbackfunctions.synchronous.SynchronousEventConsumer;
import com.baeldung.callbackfunctions.synchronous.SynchronousEventListenerImpl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class SynchronousCallbackUnitTest {

    @Test
    public void whenCallbackIsInvokedSynchronously_shouldRunSynchronousOperation(){
        EventListener listener = new SynchronousEventListenerImpl();
        SynchronousEventConsumer synchronousEventConsumer = new SynchronousEventConsumer(listener);
        String result = synchronousEventConsumer.doSynchronousOperation();

        assertNotNull(result);
        assertEquals("Synchronously running callback function", result);
    }
}
