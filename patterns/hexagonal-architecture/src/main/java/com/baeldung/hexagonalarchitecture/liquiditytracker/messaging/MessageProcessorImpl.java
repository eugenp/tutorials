package com.baeldung.hexagonalarchitecture.liquiditytracker.messaging;

import java.util.Queue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.baeldung.hexagonalarchitecture.liquiditytracker.protoapi.LiquidityTrackerIncomingMessage;
import com.google.protobuf.InvalidProtocolBufferException;

/**
 * @author Víctor Gil
 *
 * since March 2019
 */
public class MessageProcessorImpl implements MessageProcessor {
    private static final Logger log = LoggerFactory.getLogger(MessageProcessorImpl.class);

    private final Queue<LiquidityTrackerIncomingMessage> queue;

    public MessageProcessorImpl(Queue<LiquidityTrackerIncomingMessage> queue) {
        this.queue = queue;
    }

    @Override
    public void process(byte[] data) {
        LiquidityTrackerIncomingMessage incomingMessage;

        try {
            incomingMessage = LiquidityTrackerIncomingMessage.parseFrom(data);
        } catch (InvalidProtocolBufferException ex) {
            log.error("Unable to deserialize " + LiquidityTrackerIncomingMessage.class.getSimpleName(), ex);
            return;
        }

        boolean success = queue.offer(incomingMessage);
        if (!success) {
            log.error("Unable to process message because the internal queue if full. Message: " + incomingMessage);
        }
    }
}
