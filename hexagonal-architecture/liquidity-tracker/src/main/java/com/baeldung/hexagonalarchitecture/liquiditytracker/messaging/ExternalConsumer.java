package com.baeldung.hexagonalarchitecture.liquiditytracker.messaging;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.rabbitmq.client.AMQP.BasicProperties;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

/**
 * @author VictorGil
 *
 * since March 2019
 */
public class ExternalConsumer extends DefaultConsumer {
    private static final Logger log = LoggerFactory.getLogger(ExternalConsumer.class);
 
    private final MessageProcessor processor;
    
    public ExternalConsumer(Channel channel, MessageProcessor processor) {
        super(channel);
        this.processor = processor;
    }
    
    @Override
    public void handleDelivery(String consumerTag, Envelope envelope, BasicProperties properties,
            byte[] data) throws IOException{        
        log.trace("Received message, number of bytes: " + data.length);
        processor.process(data);                
    }
}
