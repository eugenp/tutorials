package com.baeldung.disruptor;

import com.lmax.disruptor.BusySpinWaitStrategy;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.WaitStrategy;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;
import com.lmax.disruptor.util.DaemonThreadFactory;
import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.ThreadFactory;

public class DisruptorIntegrationTest {
    private Disruptor<ValueEvent> disruptor;
    private WaitStrategy waitStrategy;

    @Before
    public void setUp() throws Exception {
        waitStrategy = new BusySpinWaitStrategy();
    }

    private void createDisruptor(final ProducerType producerType, final EventConsumer eventConsumer) {
        final ThreadFactory threadFactory = DaemonThreadFactory.INSTANCE;
        disruptor = new Disruptor<>(ValueEvent.EVENT_FACTORY, 16, threadFactory, producerType, waitStrategy);
        disruptor.handleEventsWith(eventConsumer.getEventHandler());
    }

    private void startProducing(final RingBuffer<ValueEvent> ringBuffer, final int count, final EventProducer eventProducer) {
        eventProducer.startProducing(ringBuffer, count);
    }

    @Test
    public void whenMultipleProducerSingleConsumer_thenOutputInFifoOrder() {
        final EventConsumer eventConsumer = new SingleEventPrintConsumer();
        final EventProducer eventProducer = new DelayedMultiEventProducer();
        createDisruptor(ProducerType.MULTI, eventConsumer);
        final RingBuffer<ValueEvent> ringBuffer = disruptor.start();

        startProducing(ringBuffer, 32, eventProducer);

        disruptor.halt();
        disruptor.shutdown();
    }

    @Test
    public void whenSingleProducerSingleConsumer_thenOutputInFifoOrder() {
        final EventConsumer eventConsumer = new SingleEventConsumer();
        final EventProducer eventProducer = new SingleEventProducer();
        createDisruptor(ProducerType.SINGLE, eventConsumer);
        final RingBuffer<ValueEvent> ringBuffer = disruptor.start();

        startProducing(ringBuffer, 32, eventProducer);

        disruptor.halt();
        disruptor.shutdown();
    }

    @Test
    public void whenSingleProducerMultipleConsumer_thenOutputInFifoOrder() {
        final EventConsumer eventConsumer = new MultiEventConsumer();
        final EventProducer eventProducer = new SingleEventProducer();
        createDisruptor(ProducerType.SINGLE, eventConsumer);
        final RingBuffer<ValueEvent> ringBuffer = disruptor.start();

        startProducing(ringBuffer, 32, eventProducer);

        disruptor.halt();
        disruptor.shutdown();
    }

    @Test
    public void whenMultipleProducerMultipleConsumer_thenOutputInFifoOrder() {
        final EventConsumer eventConsumer = new MultiEventPrintConsumer();
        final EventProducer eventProducer = new DelayedMultiEventProducer();
        createDisruptor(ProducerType.MULTI, eventConsumer);
        final RingBuffer<ValueEvent> ringBuffer = disruptor.start();

        startProducing(ringBuffer, 32, eventProducer);

        disruptor.halt();
        disruptor.shutdown();
    }
}
