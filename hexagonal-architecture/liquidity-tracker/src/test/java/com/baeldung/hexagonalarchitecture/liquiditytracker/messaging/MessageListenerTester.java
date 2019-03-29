package com.baeldung.hexagonalarchitecture.liquiditytracker.messaging;

import java.io.IOException;
import java.util.Arrays;
import java.util.concurrent.TimeoutException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.baeldung.hexagonalarchitecture.liquiditytracker.protoapi.IncomingMessageType;
import com.baeldung.hexagonalarchitecture.liquiditytracker.protoapi.LiquidityTrackerIncomingMessage;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

/**
 * @author Víctor Gil
 *
 * since March 2019
 */
public class MessageListenerTester {
    private static final Logger log = LoggerFactory.getLogger(MessageListenerTester.class);

    private Connection connection;
    private Channel channel;  
    
    public static void main(String[] args){       
        log.info("Arguments: " + Arrays.toString(args));
        new MessageListenerTester().run(args[0], args[1], args[2], args[3]);
    }
    
    private void run(String brokerHost, String queueName, String username, String password){
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(brokerHost);
        factory.setUsername(username);
        factory.setPassword(password);
        
        try{
            connection = factory.newConnection();
        } catch(IOException | TimeoutException ex){
            log.error("Unable to create connection: " + ex, ex);            
            return;
        }
        
        try {
            channel = connection.createChannel();
        } catch(IOException ex){
            log.error("Unable to create channel: " + ex, ex);
            closeChannelAndConnection();
            return;
        }
        
        try{
            channel.queueDeclare(queueName, true, false, false, null);
        } catch(IOException ex){            
            log.error("Unable to declare the queue: " + ex.toString(), ex);
            closeChannelAndConnection();
            return;
        }        
        
        byte[] data = getData();
        
        try{
            channel.basicPublish("", queueName, null, data);
        } catch(IOException ex){
            log.error("Unable to publish message: " + ex.toString(), ex);
            closeChannelAndConnection();
            return;
        }
        
        log.info("Message sent, number of bytes: " + data.length);
        closeChannelAndConnection();        
    }
    
    private byte[] getData(){
        LiquidityTrackerIncomingMessage.Builder builder = LiquidityTrackerIncomingMessage.newBuilder(); 
        builder.setType(IncomingMessageType.SHUTDOWN);        
        return builder.build().toByteArray();
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
}
