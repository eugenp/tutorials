package src.test.java.com.baeldung.blockingqueue;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class BlockingQueueUnitTest {

    @Test
    public void givenArrayBlockingQueue_whenCreated_thenReturnNumberOfElementsItCanAccept()
    {
        BlockingQueue<String> arrayBlockingQueue = new ArrayBlockingQueue<>(10);
        assertEquals(10, arrayBlockingQueue.remainingCapacity());
    }

    @Test
    public void givenLinkedBlockingQueue_whenCreated_thenReturnNumberOfElementsItCanAccept()
    {
        BlockingQueue<String> linkedBlockingQueue = new LinkedBlockingQueue<>(10);
        assertEquals(10, linkedBlockingQueue.remainingCapacity());
    }
}