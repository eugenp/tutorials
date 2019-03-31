package com.baeldung.hexagonalarchitecture.liquiditytracker.messaging;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.baeldung.hexagonalarchitecture.liquiditytracker.lifecycle.Stoppable;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * @author Víctor Gil
 *
 * since March 2019
 */
public class MessageListener implements Stoppable{
    private static final Logger log = LoggerFactory.getLogger(MessageListener.class);
    
    private volatile boolean stop = false;
    
    private Connection connection;
    private String queueName;
    
    private Channel channel;
    
    private MessageProcessor processor;
    
    public void start() throws IOException{        
        try {
            channel = connection.createChannel();
        } catch(IOException ex){
            log.error("FATAL: Unable to create channel: " + ex, ex);
            closeChannelAndConnection();
            throw ex;
        }
        
        try{
            channel.queueDeclare(queueName, true, false, false, null);
        } catch(IOException ex){            
            log.error("FATAL: Unable to declare the queue: " + ex.toString(), ex);
            closeChannelAndConnection();
            throw ex;
        }
        
        boolean autoAckTrue = true;
        try{
            channel.basicConsume(queueName, autoAckTrue, new ExternalConsumer(channel, processor));
        } catch(IOException ex){
            log.error("FATAL: Unable to start consumer: " + ex.toString(), ex);
            closeChannelAndConnection();
            throw ex;
        }
        
        log.info("Listening on queue " + queueName + ", waiting for messages");
        while(!stop){
            try{
                TimeUnit.SECONDS.sleep(1);
            } catch(InterruptedException ex){
                log.error("FATAL: Thread was interrupted while sleeping: " + ex.toString(), ex);
            } 
        }
        
        closeChannelAndConnection();
    }
    
    @Override
    public void stop(){
        log.info("We have been told to stop");
        stop = true;
    }
    
    private void closeChannelAndConnection(){
        log.info("Closing the channel and the connection (if required).");
        if (channel != null){            
            try{
                channel.close();
            } catch(IOException | TimeoutException ex){
                log.error("Unabel to close the channel: " + ex.toString(), ex);                
            }             
        }        
        if (connection != null){
            try{
                connection.close();
            } catch(IOException ex){
                log.error("Unable to close the connection: " + ex.toString(), ex);
            }
        }   
    }

    public void setProcessor(MessageProcessor processor) {
        this.processor = processor;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public void setQueueName(String queueName) {
        this.queueName = queueName;
    }    
}
