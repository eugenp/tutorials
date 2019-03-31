package com.baeldung.hexagonalarchitecture.liquiditytracker.dispatch;

import java.util.concurrent.BlockingQueue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.baeldung.hexagonalarchitecture.liquiditytracker.lifecycle.Stoppable;
import com.baeldung.hexagonalarchitecture.liquiditytracker.protoapi.LiquidityTrackerIncomingMessage;

/**
 * @author VictorGil
 *
 * since March 2019
 */
public class InternalConsumer implements Runnable, Stoppable{
    private static final Logger log = LoggerFactory.getLogger(InternalConsumer.class);
    
    private final BlockingQueue<LiquidityTrackerIncomingMessage> queue;
    
    private final InternalProcessor processor;
    
    private volatile boolean stop;
    
    public InternalConsumer(BlockingQueue<LiquidityTrackerIncomingMessage> queue, InternalProcessor processor){
        this.queue = queue;
        this.processor = processor;
    }
    
    @Override
    public void run() {

        while(!stop) {
            LiquidityTrackerIncomingMessage message = null;
            try {
                message = queue.take();
            } catch (InterruptedException ex) {
                log.info("Thread interrupted: " + ex);
                break;
            }
            processor.process(message);
        }
        log.info("We stopped waiting for messages on the internal blocking queue");
    }

    @Override
    public void stop() {
        log.info("We have been told to stop.");
        stop = true;       
    }
}
