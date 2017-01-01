package com.baeldung.disruptor;

import java.util.concurrent.ThreadFactory;
import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import com.lmax.disruptor.BusySpinWaitStrategy;
import com.lmax.disruptor.EventHandler;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.WaitStrategy;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;
import com.lmax.disruptor.util.DaemonThreadFactory;

@SuppressWarnings("unchecked")
public class DisruptorTest {
    private int expectedValue = -1;
    private int otherExpectedValue = -1;
    private Disruptor<ValueEvent> disruptor;
    private WaitStrategy waitStrategy;

    @Before
    public void setUp() throws Exception {
        waitStrategy = new BusySpinWaitStrategy();
    }
    
    @After
    public void tearDown() throws Exception {
        disruptor.halt();
        disruptor.shutdown();
    }

    private void assertExpectedValue(final int id) {
        assertEquals(++expectedValue, id);
    }
    
    private void assertOtherExpectedValue(final int id) {
        assertEquals(++otherExpectedValue, id);
    }

    private void createDisruptor(final ProducerType producerType, final EventHandler<ValueEvent>... eventHandlers) {
        final ThreadFactory threadFactory = DaemonThreadFactory.INSTANCE;
        disruptor = new Disruptor<ValueEvent>(ValueEvent.EVENT_FACTORY, 16, threadFactory, producerType, waitStrategy);
        disruptor.handleEventsWith(eventHandlers);
    }

    private EventHandler<ValueEvent> getEventHandler() {
        final EventHandler<ValueEvent> eventHandler = (event, sequence, endOfBatch) -> assertExpectedValue(event.getValue());
        return eventHandler;
    }
    
    private EventHandler<ValueEvent>[] getEventHandlers() {
        final EventHandler<ValueEvent> eventHandler = (event, sequence, endOfBatch) -> assertExpectedValue(event.getValue());
        final EventHandler<ValueEvent> otherEventHandler = (event, sequence, endOfBatch) -> assertOtherExpectedValue(event.getValue());
        return new EventHandler[] {eventHandler, otherEventHandler};
    }

    private void produce(final RingBuffer<ValueEvent> ringBuffer) {
        for (int i = 0; i < 32; i++) {
            final long seq = ringBuffer.next();
            final ValueEvent valueEvent = ringBuffer.get(seq);
            valueEvent.setValue(i);
            ringBuffer.publish(seq);
        }
    }
    
    private void startProducer(final RingBuffer<ValueEvent> ringBuffer) {
        final Runnable runnable = () -> produce(ringBuffer);
        new Thread(runnable).start();
    }
    
    @Test
    public void whenSingleProducerSingleConsumer_thenOutputInFifoOrder() {
        createDisruptor(ProducerType.SINGLE, getEventHandler());
        final RingBuffer<ValueEvent> ringBuffer = disruptor.start();
        startProducer(ringBuffer);
    }
    
    @Test
    public void whenSingleProducerMultipleConsumer_thenOutputInFifoOrder() {
        createDisruptor(ProducerType.SINGLE, getEventHandlers());
        final RingBuffer<ValueEvent> ringBuffer = disruptor.start();
        startProducer(ringBuffer);
    }
}
